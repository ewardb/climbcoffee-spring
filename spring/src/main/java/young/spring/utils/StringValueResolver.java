package young.spring.utils;


/**
 * Simple strategy interface for resolving a String value.
 * Used by {@link young.spring.beans.factory.config.ConfigurableBeanFactory}.
 * <p>
 *
 *
 */
public interface StringValueResolver {


    String resolveStringValue(String strVal);


}
