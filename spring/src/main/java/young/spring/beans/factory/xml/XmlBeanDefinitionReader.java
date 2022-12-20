package young.spring.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import org.apache.logging.log4j.util.Strings;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import young.spring.beans.BeansException;
import young.spring.beans.PropertyValue;
import young.spring.beans.factory.config.BeanDefinition;
import young.spring.beans.factory.config.BeanReference;
import young.spring.beans.factory.support.AbstractBeanDefinitionReader;
import young.spring.beans.factory.support.BeanDefinitionRegistry;
import young.spring.context.annotation.ClassPathBeanDefinitionScanner;
import young.spring.core.io.Resource;
import young.spring.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


/**
 * xml beanDefinitionReader 器
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {


    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try (InputStream stream = resource.getInputStream()) {
            doLoadBeanDefinitions(stream);
        } catch (IOException | ClassNotFoundException | DocumentException e) {

        }
    }

    @Override
    public void loadBeanDefinitions(Resource... resources) throws BeansException {
        for (Resource resource : resources) {
            loadBeanDefinitions(resource);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        Resource resource = getResourceLoader().getResource(location);
        loadBeanDefinitions(resource);
    }

    @Override
    public void loadBeanDefinitions(String... locations) throws BeansException {
        for (String str : locations) {
            loadBeanDefinitions(str);
        }
    }


    /**
     * 真正加载 beanDefinition
     *
     * @param inputStream
     */
    @Deprecated
    public void doLoadBeanDefinitionsOld(InputStream inputStream) throws ClassNotFoundException, DocumentException {
        Document doc = XmlUtil.readXML(inputStream);
        Element root = doc.getDocumentElement();
        NodeList childNodes = root.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            //
            if (!(item instanceof Element)) continue;
            //如果不是bean 不执行
            if (!"bean".equals(item.getNodeName())) continue;

            //进行解析
            String id = ((Element) item).getAttribute("id");
            String name = ((Element) item).getAttribute("name");
            String aClass = ((Element) item).getAttribute("class");
            String initMethod = ((Element) item).getAttribute("init-method");
            String destroyMethodName = ((Element) item).getAttribute("destroy-method");
            String beanScope = ((Element) item).getAttribute("scope");

            Class<?> clazz = Class.forName(aClass);

            //beanName 选取优先级 id > name
            String beanName = id != null ? id : name;
            if (Strings.isBlank(beanName)) {
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            beanDefinition.setInitMethodName(initMethod);
            beanDefinition.setDestroyMethodName(destroyMethodName);
            if (StrUtil.isNotEmpty(beanScope)) {
                beanDefinition.setScope(beanScope);
            }
            // 读取属性并填充
            NodeList eles = item.getChildNodes();
            for (int j = 0; j < eles.getLength(); j++) {

                if (!(eles.item(j) instanceof Element)) continue;
                if (!"property".equals(eles.item(j).getNodeName())) continue;

                String attrName = ((Element) eles.item(j)).getAttribute("name");
                String attrValue = ((Element) eles.item(j)).getAttribute("value");
                String attrRef = ((Element) eles.item(j)).getAttribute("ref");

                // 获取属性值：引入对象、值对象
                Object value = StrUtil.isEmpty(attrRef) ? attrValue : new BeanReference(attrRef);
                PropertyValue propertyValue = new PropertyValue(attrName, value);
                if (getRegistry().containsBeanDefinition(beanName)) {
                    throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
                }
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }
            getRegistry().registerBeanDefinition(beanName, beanDefinition);
        }

    }


    /**
     * 真正加载 beanDefinition
     *
     * @param inputStream
     */
    public void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException, DocumentException {
        SAXReader saxReader = new SAXReader();
        org.dom4j.Document document = saxReader.read(inputStream);
        org.dom4j.Element rootElement = document.getRootElement();

        // 解析 context:component-scan 标签，扫描包中的类并提取相关信息，用于组装 BeanDefinition
        org.dom4j.Element componentScan = rootElement.element("component-scan");
        if (null != componentScan) {
            String scanPath = componentScan.attributeValue("base-package");
            if (StrUtil.isEmpty(scanPath)) {
                throw new BeansException("The value of base-package attribute can not be empty or null");
            }
            scanPackage(scanPath);
        }
        List<org.dom4j.Element> beans = rootElement.elements("bean");
        for (org.dom4j.Element bean : beans) {
            String id = bean.attributeValue("id");
            String name = bean.attributeValue("name");
            String aClass = bean.attributeValue("class");
            String initMethod = bean.attributeValue("init-method");
            String destroyMethodName = bean.attributeValue("destroy-method");
            String beanScope = bean.attributeValue("scope");

            Class<?> clazz = Class.forName(aClass);

            //beanName 选取优先级 id > name
            String beanName = id != null ? id : name;
            if (Strings.isBlank(beanName)) {
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }

            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            beanDefinition.setInitMethodName(initMethod);
            beanDefinition.setDestroyMethodName(destroyMethodName);
            if (StrUtil.isNotEmpty(beanScope)) {
                beanDefinition.setScope(beanScope);
            }

            List<org.dom4j.Element> propertyList = bean.elements("property");

            for (org.dom4j.Element property : propertyList) {

                String attrName = property.attributeValue("name");
                String attrValue = property.attributeValue("value");
                String attrRef = property.attributeValue("ref");


                // 获取属性值：引入对象、值对象
                Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
                // 创建属性信息
                PropertyValue propertyValue = new PropertyValue(attrName, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }
            if (getRegistry().containsBeanDefinition(beanName)) {
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
            }
            // 注册 BeanDefinition
            getRegistry().registerBeanDefinition(beanName, beanDefinition);

        }


    }

    private void scanPackage(String scanPath) {
        String[] basePackages = StrUtil.splitToArray(scanPath, ',');
        ClassPathBeanDefinitionScanner classPathBeanDefinitionScanner = new ClassPathBeanDefinitionScanner(getRegistry());
        classPathBeanDefinitionScanner.doScan(basePackages);
    }

}
