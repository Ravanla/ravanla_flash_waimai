package cn.ravanla.flash.code;

import org.nutz.lang.Strings;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 属性（数据库列）基本信息描述<br>
 * 作者: zhangtao <br>
 * 创建日期: 16-7-5<br>
 */
public class ColumnDescriptor {
    private static Map<String, Class<?>> typeMapping = new HashMap<String, Class<?>>();

    /*
    * 这是一个静态代码块，其中typeMapping是一个静态的HashMap对象，
    * 它存储了数据库表字段类型和Java类型之间的映射关系。
    * 静态代码块在类加载时执行，用于初始化静态变量和执行一些静态的初始化代码。
    * 在这个代码块中，将常用的数据库类型映射为对应的Java类型，
    * 方便后续代码中将查询结果集中的数据类型转换为对应的Java类型。
    * 例如，当查询结果集中包含了一个类型为varchar的字段时，程序会将它转换为String类型。
    * */
    static {
        typeMapping.put("varchar", String.class);
        typeMapping.put("enum", String.class);
        typeMapping.put("bigint", Long.class);
        typeMapping.put("long", Long.class);
        typeMapping.put("integer", Integer.class);
        typeMapping.put("float", Float.class);
        typeMapping.put("double", Double.class);
        typeMapping.put("int", Integer.class);
        typeMapping.put("timestamp", Integer.class);
        typeMapping.put("datetime", Integer.class);
        typeMapping.put("boolean", boolean.class);
        typeMapping.put("tinyint", boolean.class);
        typeMapping.put("bool", boolean.class);
        typeMapping.put("decimal", BigDecimal.class);
    }
    // todo
    // private static Map<String, Class<?>> validationRules = new HashMap<String,Class<?>>();

    static {

    }

    private static Map<String, String> labelMapping = new HashMap<String, String>();

    /*
    * typeMapping 是一个将数据库中的数据类型映射到 Java 类型的映射表。
    * 例如，将 MySQL 中的 "bigint" 映射到 Java 的 "Long" 类型，
    * 将 "varchar" 映射到 Java 的 "String" 类型。
    *
    * labelMapping 则是用于前端展示的标签和对应的值之间的映射表。
    * 通常情况下，它们是从数据库中取得的。
    * 例如，假设有一个数据库表 "users"，其中有一列 "gender"，它的值是 "M" 或 "F"。
    * 在前端展示用户列表时，你可能需要将 "M" 映射成 "男性"，"F" 映射成 "女性"，
    * 这时候就需要使用 labelMapping。
    * 具体实现方式可能因框架而异，但通常是通过一个 Map 或类似的数据结构来实现映射关系。
    * */
    static {

        labelMapping.put("id", "ID");
        labelMapping.put("opAt", "操作时间");
        labelMapping.put("opBy", "操作人");
        labelMapping.put("delFlag", "删除标记");
    }

    /*
    * 这段代码定义了一个名为Validation的静态内部类，该类包含两个成员变量klass和annotation，
    * 以及一个构造函数和一个getAnnotation方法。
    * */
    public static class Validation {
        // klass是一个Class<?>类型的变量，表示需要验证的对象的类型，
        // 而annotation是一个String类型的变量，表示使用的注解类型。
        public final Class<?> klass;
        private final String annotation;

        // 构造函数Validation(Class<?> klass, String annotation)用于初始化类中的成员变量klass和annotation，
        // 将它们作为参数传递给构造函数。
        public Validation(Class<?> klass, String annotation) {
            this.klass = klass;
            this.annotation = annotation;
        }

        // getAnnotation方法返回annotation变量的值，即使用的注解类型。
        public String getAnnotation() {
            return annotation;
        }
    }

    private static Pattern COLUMN_TYPE_PATTERN = Pattern.compile("^(\\w+)(?:\\((\\d+)\\))?");
    private static Pattern ENUM_PATTERN = Pattern.compile("enum\\((.+)\\)");

    public String columnName;
    private String label;
    public boolean primary;
    public String dataType;

    public String columnType;
    public int size;

    public boolean nullable;
    private Object defaultValue;
    private String comment;
    private String fieldName;

    private List<String> enumValues = new ArrayList<String>();

    private List<Validation> validations = new ArrayList<Validation>();
    private boolean validationBuilt = false;

    private String queryOperator;

    private boolean containsValidation(Class<?> klass) {
        for (Validation v : validations) {
            if (v.klass == klass) {
                return true;
            }
        }
        return false;
    }

    public boolean hasLabel() {
        return label != null;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        if (label != null) {
            return label;
        }
        String defaultLabel = labelMapping.get(columnName);
        if (defaultLabel != null) {
            return defaultLabel;
        }
        return label;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
        if (comment == null) {
            return;
        }

        extractLabel(comment);
        extractSearchable(comment);
    }

    Pattern labelPattern = Pattern.compile("label:\\s*([^,;，]+)");

    private void extractLabel(String comment) {
        Matcher m = labelPattern.matcher(comment);
        if (m.find()) {
            this.label = m.group(1);
        }
    }

    public String getQueryOperator() {
        return queryOperator;
    }

    Pattern queryPattern = Pattern.compile("searchable:\\s*(\\w+)");

    private void extractSearchable(String comment) {
        // searchable: eq
        Matcher m = queryPattern.matcher(comment);
        if (m.find()) {
            queryOperator = m.group(1);
        }
    }

    public String getColumnName() {
        return columnName;
    }

    public String getFieldName() {
        if (Strings.isBlank(fieldName)) {
            return Utils.lowerCamel(columnName);
        }
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public List<String> getEnumValues() {
        return enumValues;
    }

    public void setColumnType(String columnType) {
        Matcher m = ENUM_PATTERN.matcher(columnType);
        if (m.find()) {
            this.columnType = "enum";

            String s = m.group(1);
            for (String v : s.split(",")) {
                v = v.trim().replaceAll("'", "");
                enumValues.add(v);
            }
            return;
        }

        m = COLUMN_TYPE_PATTERN.matcher(columnType);
        if (m.find()) {
            if (m.group(2) != null) {
                this.size = Integer.parseInt(m.group(2));
            }
            this.columnType = m.group(1);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public String getJavaType() {
        if ("tinyint".equalsIgnoreCase(dataType) && size == 1) {
            return boolean.class.getName();
        }
        if ("enum".equalsIgnoreCase(dataType)) {
            return getUpperJavaFieldName();
        }
        Class<?> type = typeMapping.get(dataType);
        if (type != null) {
            return type.getName();
        }

        return String.class.getName();
    }

    public String getSimpleJavaTypeName() {
        return getJavaType().replaceFirst("^.*\\.", "");
    }

    public boolean isEnum() {
        return "enum".equalsIgnoreCase(dataType);
    }

    public boolean isBoolean() {
        return boolean.class.getName().equals(getJavaType());
    }

    public boolean isTimestamp() {
        return "timestamp".equalsIgnoreCase(dataType);
    }

    public String getUpperJavaFieldName() {
        return Utils.lowerCamel(columnName);
        // return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.upperCamel,
        // columnName);
    }

    public String getGetterMethodName() {
        if (isBoolean()) {
            return "is" + getUpperJavaFieldName();
        }
        return "get" + getUpperJavaFieldName();
    }

    public String getSetterMethodName() {
        return "set" + getUpperJavaFieldName();
    }

    public String getColumnAnnotation() {
        if (primary) {
            // return "@Id";
            return "@Name\r\n	@Prev(els = {@EL(\"uuid()\")})";
        }
        return "@Column";
    }

    public void setDefaultValue(Object v) {
        this.defaultValue = v;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public String getDefaultValueCode() {
        if (isEnum()) {
            return getSimpleJavaTypeName() + "." + defaultValue;
        }
        if (isBoolean()) {
            if ("1".equals(defaultValue.toString())) {
                return "true";
            } else {
                return "false";
            }
        }
        if (isTimestamp()) {
            if (("0000-00-00 00:00:00".equals(defaultValue) || "CURRENT_TIMESTAMP".equals(defaultValue))) {
                return "DateTime.now()";
            }
        }
        if (defaultValue != null && Long.class.getName().equals(getJavaType())) {
            return defaultValue + "L";
        }
        if (defaultValue != null && BigDecimal.class.getName().equals(getJavaType())) {
            return "new BigDecimal(\"" + defaultValue.toString() + "\")";
        }
        return "\"" + getDefaultValue().toString() + "\"";
    }

    // TODO
    public String getValidationFormClass() {
        return "";
    }

}