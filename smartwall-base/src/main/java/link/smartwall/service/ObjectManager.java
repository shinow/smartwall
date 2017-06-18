/**
 * SmartWall(2013)
 */
package link.smartwall.service;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.DB;
import org.nutz.dao.Dao;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.Readonly;
import org.nutz.dao.entity.annotation.SQL;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.View;
import org.nutz.ioc.loader.annotation.IocBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.AnnotationMemberValue;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.BooleanMemberValue;
import javassist.bytecode.annotation.EnumMemberValue;
import javassist.bytecode.annotation.MemberValue;
import javassist.bytecode.annotation.StringMemberValue;
import link.smartwall.basemodel.AbstractObject;
import link.smartwall.basemodel.ColumnCatalog;
import link.smartwall.basemodel.ColumnProperty;
import link.smartwall.basemodel.Model;
import link.smartwall.basemodel.ModelColumn;
import link.smartwall.util.StrUtils;

/**
 * 
 * 业务基础模型管理器
 * 
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since  2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-1-9 lexloo
 *        </pre>
 */
@IocBean(args = {"refer:dao"})
public class ObjectManager extends BaseService {
    public ObjectManager(Dao dao) {
        super(dao);
    }

    /**
     * 审核模型Code
     */
    private static final String AUDIT_CODE_SUFFIX = "_audit";
    /**
     * 审核类后缀
     */
    private static final String AUDIT_CLASS_SUFFIX = "Audit";
    /**
     * 审核状态， 0 待审核
     */
    private static final int AUDIT_STATUS_0 = 0;
    /**
     * 审核状态， 1 已审核
     */
    private static final int AUDIT_STATUS_1 = 1;
    /**
     * 审核状态， 2 审核未通过
     */
    private static final int AUDIT_STATUS_2 = 2;
    /**
     * 审核状态， 3 已删除
     */
    private static final int AUDIT_STATUS_3 = 3;
    /**
     * 删除状态， 0 正常
     */
    // private static final int DEL_STATUS_0 = 0;
    /**
     * 删除状态， 1 已删除
     */
    // private static final int DEL_STATUS_1 = 1;
    /**
     * 审核状态
     */
    public static final Map<Integer, String> AUDIT_STATUS_NAME = new HashMap<Integer, String>();
    static {
        AUDIT_STATUS_NAME.put(AUDIT_STATUS_0, "待审核");
        AUDIT_STATUS_NAME.put(AUDIT_STATUS_1, "已审核");
        AUDIT_STATUS_NAME.put(AUDIT_STATUS_2, "审核未通过");
        AUDIT_STATUS_NAME.put(AUDIT_STATUS_3, "已删除");
    }
    /**
     * 模拟数据
     */
    private static final int SAMPLE_DATA_INT = 3;
    /**
     * 模拟数据
     */
    private static final double SAMPLE_DATA_DOUBLE = 3.333;
    /**
     * 对象类缓存
     */
    private Map<String, Class<? extends AbstractObject>> modelClazzs =
            Collections.synchronizedMap(new HashMap<String, Class<? extends AbstractObject>>());
    /**
     * 日志记录器
     */
    private Logger logger = LoggerFactory.getLogger(ObjectManager.class.getName());

    /**
     * 获取所有生成类列表,可用于检测程序处理
     * 
     * @return 生成类列表
     */
    public Collection<Class<? extends AbstractObject>> getAllGeneratedClass() {
        return Collections.unmodifiableCollection(this.modelClazzs.values());
    }

    /**
     * 根据代码创建业务对象
     * 
     * @param code 业务对象代码
     * @return 业务对象
     */
    public AbstractObject createObject(String code) {
        Class<?> clazz = this.getObjectClass(code);

        if (clazz == null) {
            logger.warn("模型'{}'的业务模型类为空", code);
        } else {
            try {
                return (AbstractObject) clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 通过模型代码获取模型类
     * 
     * @param code 模型代码
     * @return 模型实例
     */
    public synchronized Class<? extends AbstractObject> getObjectClass(String code) {
        Class<? extends AbstractObject> clazz = this.modelClazzs.get(code);

        if (clazz == null) {
            // TODO 在函数级别简单实用同步，需要改造
            Model model = dao().fetch(Model.class, code);
            if (model.isUseExistObj()) {
                if (logger.isDebugEnabled()) {
                    logger.debug("增强业务模型类:{}", code);
                }

                clazz = this.enhanceExistClazz(model);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("注册业务模型类:{}", code);
                }

                dao().fetchLinks(model, "columns");

                clazz = this.makeObjectClazz(model);
            }

            this.modelClazzs.put(code, clazz);
        }

        return clazz;
    }

    /**
     * 通过现有类扩展
     * 
     * @param className 类名
     * @return 扩展后的类
     */
    @SuppressWarnings("unchecked")
    private Class<? extends AbstractObject> enhanceExistClazz(String className) {
        try {
            logger.info("增强类:{}", className);

            Class<? extends AbstractObject> clazz = (Class<? extends AbstractObject>) Class.forName(className);

            return enhance(clazz, clazz.getName() + "Ehanced");
        } catch (Exception e) {
            e.printStackTrace();
            // throw new IllegalStateException("对象增强出错");
        }

        return null;
    }

    /**
     * 增强所有现有类,在系统启动时处理
     * 
     * @param objList 对象列表
     */
    public void enhanceExistClazzs(Map<String, Class<? extends AbstractObject>> objList) {
        if (logger.isDebugEnabled()) {
            logger.debug("增强已有业务模型类");
        }

        for (Map.Entry<String, Class<? extends AbstractObject>> entry : objList.entrySet()) {
            if (logger.isDebugEnabled()) {
                logger.debug("增强类:" + entry.getValue());
            }

            this.modelClazzs.put(entry.getKey(), this.enhanceExistClazz(entry.getValue().getName()));
        }
    }

    /**
     * 新类继承现有类并扩展,类的所有属性必须能通过全部小写访问，简化界面逻辑,现有类只能是继承AbstractObject,
     * 
     * @param model 模型
     * @return 模型类
     */
    @SuppressWarnings("unchecked")
    private Class<? extends AbstractObject> enhanceExistClazz(Model model) {
        try {
            logger.info("增强类{}:{}", model.getCode(), model.getObjClass());

            Class<? extends AbstractObject> clazz =
                    (Class<? extends AbstractObject>) Class.forName(model.getObjClass());

            String clazzName = this.generateModelClazzName(model.getCode());

            return enhance(clazz, clazzName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 增强模型类
     * 
     * @param clazz 类
     * @param clazzName 类名
     * @return 增强后的类
     * @throws NotFoundException 异常
     * @throws CannotCompileException 异常
     */
    @SuppressWarnings("unchecked")
    private Class<? extends AbstractObject> enhance(Class<? extends AbstractObject> clazz, String clazzName)
            throws NotFoundException, CannotCompileException {
        ClassPool classPool = ClassPool.getDefault();
        classPool.insertClassPath(new ClassClassPath(clazz));
        // 绑定需要加载包
        classPool.importPackage("java.util");
        classPool.importPackage("com.itfsm.basemodel");
        CtClass ctClazz = classPool.makeClass(clazzName);
        CtClass sp = classPool.get(clazz.getName());
        ctClazz.setSuperclass(sp);

        // 修改entityTableName与entityIdField方法
        updateDateTableNameAndId(clazz, ctClazz);
        return ctClazz.toClass();
    }

    /**
     * 获取需要注入流程参数变量的字段列表
     * 
     * @param clazz 类名
     * @return 需要注入流程参数变量的字段列表
     */
    public final synchronized List<Field> getDeclaredFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<Field>();
        visitClassFields(clazz, fields);

        return fields;
    }

    /**
     * 查询类及父类的字段名
     * 
     * @param clazz 类名
     * @param fields 获取Field字段名
     */
    private void visitClassFields(Class<?> clazz, List<Field> fields) {
        for (Field f : clazz.getDeclaredFields()) {

            if ((f.getModifiers() & Modifier.TRANSIENT) != Modifier.TRANSIENT) {
                fields.add(f);
            }
        }

        Class<?> parentClazz = clazz.getSuperclass();
        if (parentClazz != null) {
            visitClassFields(parentClazz, fields);
        }
    }

    /**
     * 修改entityTableName与entityIdField方法<br/>
     * 2015-11-04 黄奇 增加处理，如果table，id没有设置的处理
     * 
     * @param clazz 对象类
     * @param ctClazz 对象类
     */
    private void updateDateTableNameAndId(Class<? extends AbstractObject> clazz, CtClass ctClazz) {
        // 对象对应的表，没有处理异常
        Table table = clazz.getAnnotation(Table.class);
        // 对象对应的主键字段,没有处理异常
        String idFieldName = "";
        for (Field f : getDeclaredFields(clazz)) {
            Id id = f.getAnnotation(Id.class);
            if (id != null) {
                idFieldName = f.getName();
                break;
            }
        }

        try {
            if (table != null) {
                String tableName = table.value();
                ctClazz.addMethod(CtMethod.make("public String entityTableName(){return \""
                                                + tableName
                                                + "\";}",
                                                ctClazz));
            }

            if (!StrUtils.isEmpty(idFieldName)) {
                ctClazz.addMethod(CtMethod.make("public String entityIdField(){return \""
                                                + idFieldName
                                                + "\";}",
                                                ctClazz));
            }
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据模型代码创建模型类
     * 
     * @param model 模型对象
     * @return 模型类
     */
    @SuppressWarnings("unchecked")
    private Class<? extends AbstractObject> makeObjectClazz(Model model) {
        if (logger.isDebugEnabled()) {
            logger.debug("构造引擎模型类-模型代码：{}", model.getCode());
        }

        String clazzName = this.generateModelClazzName(model.getCode());

        try {
            // 开始创建类
            ClassPool pool = ClassPool.getDefault();
            CtClass sp = null;

            // TODO 版本控制，继承AbstractVersionAbleObject
            pool.insertClassPath(new ClassClassPath(AbstractObject.class));
            sp = pool.get(AbstractObject.class.getName());

            // 扩充数据类型
            pool.importPackage("java.sql");
            pool.importPackage("org.nutz.dao");
            pool.importPackage(DB.class.getName());
            CtClass ctClazz = pool.makeClass(clazzName);
            ctClazz.setSuperclass(sp);
            ClassFile cf = ctClazz.getClassFile();
            ConstPool cp = cf.getConstPool();

            Annotation ann;
            AnnotationsAttribute attr = new AnnotationsAttribute(cp, AnnotationsAttribute.visibleTag);

            // 添加 @Table
            String tableName = model.getTableName();
            ann = new Annotation(Table.class.getName(), cp);
            ann.addMemberValue("value", new StringMemberValue(tableName, cp));
            attr.addAnnotation(ann);
            // 添加@View
            String viewName = model.getViewName();
            if (!StrUtils.isEmpty(viewName)) {
                ann = new Annotation(View.class.getName(), cp);
                ann.addMemberValue("value", new StringMemberValue(viewName, cp));
                attr.addAnnotation(ann);
            }
            cf.addAttribute(attr);

            // 创建所有字段
            List<ModelColumn> columns = model.getColumns();
            for (ModelColumn column : columns) {
                String code = column.getCode();
                System.out.println("---------------------");
                System.out.println(code);
                System.out.println("----------------------");
                ColumnCatalog catalog = column.getCatalog();
                ColumnProperty property = column.getProperty();

                switch (catalog) {
                case FIELD:
                    // 都使用小写
                    CtField field = CtField.make(this.getFilePropertyStr(column), ctClazz);

                    String fieldName = column.getField();
                    if (StrUtils.isEmpty(fieldName)) {
                        fieldName = code;
                    }

                    // 添加Column 注解
                    attr = new AnnotationsAttribute(cp, AnnotationsAttribute.visibleTag);
                    ann = new Annotation(Column.class.getName(), cp);
                    ann.addMemberValue("value", new StringMemberValue(fieldName, cp));
                    attr.addAnnotation(ann);

                    if (column.isReadonly()) {
                        ann = new Annotation(Readonly.class.getName(), cp);
                        attr.addAnnotation(ann);
                    }

                    if (property == ColumnProperty.ID) {
                        ann = new Annotation(Id.class.getName(), cp);
                        ann.addMemberValue("auto", new BooleanMemberValue(false, cp));
                        attr.addAnnotation(ann);

                        Annotation prev = new Annotation(Prev.class.getName(), cp);
                        Annotation annSql = new Annotation(SQL.class.getName(), cp);
                        String sql = String.format("select %s_s.nextval from dual", tableName);
                        annSql.addMemberValue("value", new StringMemberValue(sql, cp));

                        EnumMemberValue emv = new EnumMemberValue(cp);
                        emv.setType(DB.class.getName());
                        emv.setValue("ORACLE");
                        annSql.addMemberValue("db", emv);

                        MemberValue mSql = new AnnotationMemberValue(annSql, cp);
                        ArrayMemberValue amv = new ArrayMemberValue(mSql, cp);
                        MemberValue[] sqls = new MemberValue[1];
                        sqls[0] = mSql;
                        amv.setValue(sqls);

                        prev.addMemberValue("value", amv);

                        attr.addAnnotation(prev);
                    }

                    field.getFieldInfo().addAttribute(attr);

                    ctClazz.addField(field);

                    ctClazz.addMethod(CtMethod.make(this.getGettorFuncStr(column), ctClazz));
                    ctClazz.addMethod(CtMethod.make(this.getSettorFuncStr(column), ctClazz));
                    break;
                default:
                    // TODO 处理计算字段

                    break;
                }
            }

            // 创建继承的方法（insert,update,delete前后的操作）
            createExtendMethod(model, ctClazz);

            // 版本检查，需要 注入getVersionKey
            if (model.isVersionCheck()) {
                String getMethodStr =
                        "public String getVersionKey()" + "{return \"" + StrUtils.c2str(model.getVersionKey()) + "\";}";

                ctClazz.addMethod(CtMethod.make(getMethodStr, ctClazz));
            }

            // 修改entityTableName与entityIdField方法
            updateDateTableNameAndId(model, ctClazz);
            // 把生成的class文件写入文件
            byte[] byteArr = ctClazz.toBytecode();
            FileOutputStream fos = new FileOutputStream(new File("E://11//" + clazzName + ".class"));
            fos.write(byteArr);
            fos.close();

            return ctClazz.toClass();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("对象类生成出错");
        }
    }

    /**
     * 更新表名和Id字段
     * 
     * @param model 模型
     * @param ctClazz 生成类
     */
    private void updateDateTableNameAndId(Model model, CtClass ctClazz) {
        String idFieldName = "";
        // 查询Id字段
        List<ModelColumn> columns = model.getColumns();
        for (ModelColumn column : columns) {
            ColumnCatalog catalog = column.getCatalog();
            ColumnProperty property = column.getProperty();

            switch (catalog) {
            case FIELD:
                if (property == ColumnProperty.ID) {
                    idFieldName = column.getField();
                    if (StrUtils.isEmpty(idFieldName)) {
                        idFieldName = column.getCode();
                        break;
                    }
                }
            default:
                break;
            }
        }

        try {
            ctClazz.addMethod(CtMethod.make("public String entityTableName(){return \""
                                            + model.getTableName()
                                            + "\";}",
                                            ctClazz));
            ctClazz.addMethod(CtMethod.make("public String entityIdField(){return \"" + idFieldName + "\";}", ctClazz));
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建Get方法签名（Boolean 也使用 set）
     * 
     * @param column 列类型
     * @return 方法签名
     */
    private String getGettorFuncStr(ModelColumn column) {
        String field = column.getCode().toLowerCase();

        return String.format("public %s get%s(){return this.%s;}",
                             column.getType().getJavaType(),
                             StrUtils.capitalize(field),
                             field);
    }

    /**
     * 创建Set方法签名
     * 
     * @param column 列类型
     * @return 方法签名
     */
    private String getSettorFuncStr(ModelColumn column) {
        String field = column.getCode().toLowerCase();
        return String.format("public void set%s(%s %s){this.%s = %s;}",
                             StrUtils.capitalize(field),
                             column.getType().getJavaType(),
                             field,
                             field,
                             field);
    }

    /**
     * 获取字段属性字符串
     * 
     * @param column 列类型
     * @return 字段属性串
     */
    private String getFilePropertyStr(ModelColumn column) {
        StringBuilder sb = new StringBuilder();

        sb.append("public ").append(column.getType().getJavaType()).append(" ").append(column.getCode().toLowerCase());
        String defValue = column.getDefValue();
        if (StrUtils.isEmpty(defValue)) {
            sb.append(";");
        } else {
            sb.append("=").append(column.getType().getDefValue(defValue)).append(";");
        }

        return sb.toString();
    }

    /**
     * 获取模型类名($+code)
     * 
     * @param code 模型代码
     * @return 类名
     */
    private String generateModelClazzName(String code) {
        return "com.itfsm.basemodel.generated.LX" + code.replaceAll("\\.", "_");
    }

    /**
     * 测试所有自定义的模型类
     */
    public void testAllModel() {
        try {
            logger.info("开始验证测试所有定义的模型类");
            logger.info("各个对象插入一条记录");

            List<Model> modelList = dao().query(Model.class, Cnd.where("useExistObj", "<>", 1));
            for (Model m : modelList) {
                Object o = this.createObject(m.getCode());
                logger.info("校验模型 {}", m.getRemark());
                // 给对象赋值
                for (Field f : o.getClass().getDeclaredFields()) {
                    Class<?> type = f.getType();
                    f.setAccessible(true);
                    if (type == int.class || type == Integer.class) {
                        f.setInt(o, SAMPLE_DATA_INT);
                    } else if (type == double.class
                               || type == float.class
                               || type == Double.class
                               || type == Float.class) {
                        f.setDouble(o, SAMPLE_DATA_DOUBLE);
                    } else if (type == Timestamp.class) {
                        f.set(o, new Timestamp(Calendar.getInstance().getTimeInMillis()));
                    } else if (Date.class.isAssignableFrom(type)) {
                        f.set(o, new Date());
                    }
                }

                dao().insert(o);
            }
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 创建继承的六个方法
     * 
     * @param model 模型
     * @param ctClazz 创建的类
     */
    public void createExtendMethod(Model model, CtClass ctClazz) {
        try {
            if (model.getBeforeInsert() != null) {
                String beforeInsert =
                        "public void beforeInsert(Dao dao) {" + StrUtils.c2str(model.getBeforeInsert()) + "}";

                ctClazz.addMethod(CtMethod.make(beforeInsert, ctClazz));
            }
            if (model.getAfterInsert() != null) {
                String afterInsert =
                        "public void afterInsert(Dao dao) {" + StrUtils.c2str(model.getAfterInsert()) + "}";

                ctClazz.addMethod(CtMethod.make(afterInsert, ctClazz));
            }
            if (model.getBeforeUpdate() != null) {
                String beforeUpdate =
                        "public void beforeUpdate(Dao dao) {" + StrUtils.c2str(model.getBeforeUpdate()) + "}";

                ctClazz.addMethod(CtMethod.make(beforeUpdate, ctClazz));
            }
            if (model.getAfterUpdate() != null) {
                String afterUpdate =
                        "public void afterUpdate(Dao dao) {" + StrUtils.c2str(model.getAfterUpdate()) + "}";

                ctClazz.addMethod(CtMethod.make(afterUpdate, ctClazz));
            }
            if (model.getBeforeDelete() != null) {
                String beforeDelete =
                        "public void beforeDelete(Dao dao) {" + StrUtils.c2str(model.getBeforeDelete()) + "}";

                ctClazz.addMethod(CtMethod.make(beforeDelete, ctClazz));
            }
            if (model.getAfterDelete() != null) {
                String afterDelete =
                        "public void afterDelete(Dao dao) {" + StrUtils.c2str(model.getAfterDelete()) + "}";

                ctClazz.addMethod(CtMethod.make(afterDelete, ctClazz));
            }
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据代码创建审核业务对象
     * 
     * @param code 业务对象代码
     * @return 业务对象
     */
    // public AbstractObject createAuditObject(String code) {
    // Class<?> clazz = this.makeAuditObjectClass(code);
    //
    // if (clazz == null) {
    // logger.warn("模型'{}'的审核业务模型类为空", code);
    // } else {
    // try {
    // return (AbstractObject) clazz.newInstance();
    // } catch (InstantiationException e) {
    // e.printStackTrace();
    // } catch (IllegalAccessException e) {
    // e.printStackTrace();
    // }
    // }
    // return null;
    // }

    /**
     * 根据业务类生成通用审核子类
     * 
     * @param parentCode 业务类code
     * @return 审核子类
     */
    @SuppressWarnings("unchecked")
    // public Class<? extends AbstractObject> makeAuditObjectClass(String parentCode) {
    //
    // String auditCode = parentCode + AUDIT_CODE_SUFFIX;
    //
    // Class<? extends AbstractObject> auditClazz = this.modelClazzs.get(auditCode);
    // // 创建审核子类
    // if (auditClazz == null) {
    // // 父类
    // Class<? extends AbstractObject> parentClazz = this.getObjectClass(parentCode);
    // try {
    // ClassPool pool = ClassPool.getDefault();
    // CtClass sp = null;
    // pool.insertClassPath(new ClassClassPath(AbstractObject.class));
    // sp = pool.get(parentClazz.getName());
    // // 扩充数据类型
    // pool.importPackage("java.sql");
    // pool.importPackage("java.util");
    // pool.importPackage("java.util.Date");
    // pool.importPackage("org.nutz.dao");
    // pool.importPackage("org.nutz.trans");
    // pool.importPackage(DB.class.getName());
    // pool.importPackage("com.itfsm.util");
    // pool.importPackage("com.itfsm.base.util");
    // pool.importPackage("com.itfsm.basemodel");
    // pool.importPackage("com.dcits.basemodel");
    // pool.importPackage("com.alibaba.fastjson");
    //
    // String auditClazzName = parentClazz.getName() + AUDIT_CLASS_SUFFIX;
    // CtClass ctClazz = pool.makeClass(auditClazzName);
    // ctClazz.setSuperclass(sp);
    // ClassFile cf = ctClazz.getClassFile();
    // ConstPool cp = cf.getConstPool();
    // Annotation ann;
    // AnnotationsAttribute attr = new AnnotationsAttribute(cp, AnnotationsAttribute.visibleTag);
    // // 添加 @Table
    // Table table = parentClazz.getSuperclass().getAnnotation(Table.class);
    // String tableName = table.value() + AUDIT_CODE_SUFFIX;
    // ann = new Annotation(Table.class.getName(), cp);
    // ann.addMemberValue("value", new StringMemberValue(tableName, cp));
    // attr.addAnnotation(ann);
    // // 添加@View
    // View view = parentClazz.getSuperclass().getAnnotation(View.class);
    // String viewName = view.value() + AUDIT_CODE_SUFFIX;
    //
    // if (!StrUtils.isEmpty(viewName)) {
    // ann = new Annotation(View.class.getName(), cp);
    // ann.addMemberValue("value", new StringMemberValue(viewName, cp));
    // attr.addAnnotation(ann);
    // }
    // cf.addAttribute(attr);
    // // 添加审核子类扩展属性
    // addAuditField(ctClazz, tableName);
    // addAuditMethod(ctClazz);
    // // 把生成的class文件写入文件
    // // byte[] byteArr = ctClazz.toBytecode();
    // // FileOutputStream fos = new FileOutputStream(new File("D://11//"+parentCode
    // // + AUDIT_CLASS_SUFFIX+".class"));
    // // fos.write(byteArr);
    // // fos.close();
    //
    // this.modelClazzs.put(auditCode, ctClazz.toClass());
    //
    // } catch (Exception e) {
    // e.printStackTrace();
    // throw new IllegalStateException("审核子类生成出错");
    // }
    //
    // }
    //
    // return this.modelClazzs.get(auditCode);
    // }
    /**
     * 修改审核状态、删除标识后续操作
     * 
     * @param ctClazz 审核子类
     */
    private void addAuditMethod(CtClass ctClazz) {

        StringBuffer sb = new StringBuffer();

        try {
            String clazzName = ctClazz.getName();
            String pClazzName = ctClazz.getSuperclass().getName();

            sb.append("public void afterUpdate(Dao dao, AbstractObject prevObj) {\n");
            sb.append(" final " + clazzName + " prevAudit = (" + clazzName + ") prevObj;\n");
            sb.append(" final " + clazzName + " currAudit = this;\n");
            // sb.append("Trans.exec(new Atom() {\n");
            // sb.append("public void run() {\n");
            sb.append("if (!\"\".equals(StrUtils.c2str(prevAudit.getAudituuid(), \"\"))) {\n");
            sb.append("if (currAudit.getAuditop() == 1) {\n");

            sb.append("if (currAudit.getAuditstatus() == 1) {\n");
            sb.append(pClazzName
                      + " bizObj = ("
                      + pClazzName
                      + ")dao.fetch("
                      + pClazzName
                      + ".class, Cnd.where(\"audit_uuid\", \"=\", prevAudit.getAudituuid()));\n");

            sb.append("        if (bizObj != null) {\n");

            sb.append(pClazzName
                      + " updateObj = ("
                      + pClazzName
                      + ")JSONObject.parseObject(JSONObject.toJSONString(currAudit), "
                      + pClazzName
                      + ".class);\n");
            sb.append(" updateObj.setValue(\"id\", bizObj.getValue(\"id\"));\n");
            sb.append(" dao.update(updateObj);\n");

            sb.append("        } else {\n");

            sb.append(pClazzName
                      + " bizObj = ("
                      + pClazzName
                      + ")JSONObject.parseObject(JSONObject.toJSONString(currAudit), "
                      + pClazzName
                      + ".class);\n");
            sb.append(pClazzName + " bizObj = (" + pClazzName + ")dao.insert(bizObj);");

            sb.append("dao.update("
                      + pClazzName
                      + ".class, Chain.make(\"audit_uuid\", prevAudit.getAudituuid()),"
                      + "  Cnd.where(\"id\", \"=\", bizObj.getValue(\"id\")));\n");

            sb.append("}\n");

            sb.append("}\n");

            sb.append("prevAudit.setAuditstatus(currAudit.getAuditstatus());");
            sb.append("prevAudit.setAuditremark(currAudit.getAuditremark());");
            sb.append("prevAudit.setAuditdate(new Date());");
            sb.append("prevAudit.setAudituser(com.itfsm.base.http.UserManager.getCurrentUser().getId());");
            sb.append("prevAudit.setAuditusername(com.itfsm.base.http.UserManager.getCurrentUser().getName());");
            // sb.append("}else{\n");

            // sb.append("prevAudit.setAuditstatus(0);\n");

            // sb.append("}\n");
            sb.append("prevAudit.setAuditstatusname(StrUtils.c2str"
                      + "(ObjectManager.AUDIT_STATUS_NAME.get(new Integer(currAudit.getAuditstatus())),\"\"));");
            sb.append("dao.update(prevAudit);\n");
            sb.append("}\n");
            sb.append("}\n");
            // sb.append("}\n");
            // sb.append("});\n");

            sb.append("}");

            ctClazz.addMethod(CtMethod.make(sb.toString(), ctClazz));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 添加审核类属性
     * 
     * @param ctClazz 审核子类
     * @param tableName 审核数据表
     */
    // private void addAuditField(CtClass ctClazz, String tableName) {
    //
    // try {
    // BaseAudit baseClazz = new BaseAudit();
    // Field[] fields = baseClazz.getClass().getDeclaredFields();
    //
    // for (Field field : fields) {
    // // 添加属性
    // addAuditSubField(field, baseClazz, ctClazz, tableName);
    // // 添加方法
    // addAuditSubFieldMethod(field, ctClazz);
    //
    // }
    //
    // } catch (Exception e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    //
    // }

    /**
     * 添加审核子类属性
     * 
     * @param field 属性
     * @param baseClazz 实例
     * @param ctClazz 生成类
     * @param tableName 审核数据表
     */
    // private void addAuditSubField(Field field, BaseAudit baseClazz, CtClass ctClazz, String tableName) {
    // StringBuilder sb = new StringBuilder();
    // try {
    // String fieldType = "";
    // field.setAccessible(true);
    // String defValue = StrUtils.c2str(field.get(baseClazz), "");
    // if ("audituuid".equals(field.getName().toLowerCase())) {
    // defValue = "StrUtils.uuid()";
    // }
    //
    // if ("class java.lang.String".equals(field.getGenericType().toString())) {
    // fieldType = "String";
    // if (!"".equals(defValue)) {
    // defValue = "\"" + defValue + "\"";
    // }
    // }
    // if ("int".equals(field.getGenericType().toString())) {
    // fieldType = "int";
    // }
    // if ("class java.util.Date".equals(field.getGenericType().toString())) {
    // fieldType = "Date";
    // if (!"".equals(defValue)) {
    // defValue = "\"" + defValue + "\"";
    // }
    // }
    // sb.append("public ").append(fieldType).append(" ").append(field.getName().toLowerCase());
    // if ("".equals(defValue)) {
    // sb.append(";");
    // } else {
    // sb.append("=").append(defValue).append(";");
    // }
    // String strField = sb.toString();
    // if ("audituuid".equals(field.getName().toLowerCase())) {
    // strField = strField.replaceAll("\"", "");
    // }
    // CtField f = CtField.make(strField, ctClazz);
    // ClassFile cf = ctClazz.getClassFile();
    // ConstPool cp = cf.getConstPool();
    // Annotation ann;
    // AnnotationsAttribute attr = new AnnotationsAttribute(cp, AnnotationsAttribute.visibleTag);
    //
    // if (field.getAnnotation(Column.class) != null) {
    // ann = new Annotation(Column.class.getName(), cp);
    // ann.addMemberValue("value", new StringMemberValue(field.getAnnotation(Column.class).value(), cp));
    // attr.addAnnotation(ann);
    // f.getFieldInfo().addAttribute(attr);
    // }
    // // 主键
    // if ("id".equals(field.getName().toLowerCase())) {
    // ann = new Annotation(Id.class.getName(), cp);
    // ann.addMemberValue("auto", new BooleanMemberValue(false, cp));
    // attr.addAnnotation(ann);
    //
    // Annotation prev = new Annotation(Prev.class.getName(), cp);
    // Annotation annSql = new Annotation(SQL.class.getName(), cp);
    // String sql = String.format("select %s_s.nextval from dual", tableName);
    // annSql.addMemberValue("value", new StringMemberValue(sql, cp));
    //
    // EnumMemberValue emv = new EnumMemberValue(cp);
    // emv.setType(DB.class.getName());
    // emv.setValue("ORACLE");
    // annSql.addMemberValue("db", emv);
    //
    // MemberValue mSql = new AnnotationMemberValue(annSql, cp);
    // ArrayMemberValue amv = new ArrayMemberValue(mSql, cp);
    // MemberValue[] sqls = new MemberValue[1];
    // sqls[0] = mSql;
    // amv.setValue(sqls);
    // prev.addMemberValue("value", amv);
    // attr.addAnnotation(prev);
    // f.getFieldInfo().addAttribute(attr);
    // }
    // ctClazz.addField(f);
    //
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    //
    // }

    /**
     * 添加审核子类属性方法
     * 
     * @param field 审核子类属性
     * @param ctClazz 生成类
     * 
     */
    private void addAuditSubFieldMethod(Field field, CtClass ctClazz) {

        try {
            String fn = field.getName();
            String fnL = fn.toLowerCase();
            Class<?> type = field.getType();
            String setMethodStr = "public void set"
                                  + StrUtils.capitalize(fnL)
                                  + "("
                                  + type.getSimpleName()
                                  + " "
                                  + fn
                                  + "){this."
                                  + fnL
                                  + "="
                                  + fn
                                  + ";}";
            ctClazz.addMethod(CtMethod.make(setMethodStr, ctClazz));

            String getMethodStr = "public "
                                  + type.getSimpleName()
                                  + " get"
                                  + StrUtils.capitalize(fnL)
                                  + "(){return this."
                                  + fn
                                  + ";}";

            ctClazz.addMethod(CtMethod.make(getMethodStr, ctClazz));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param args args
     */
    // public static void main(String[] args) {
    // String dsFileName =
    // System.getenv("XSB_HOME")
    // + File.separator
    // + "conf"
    // + File.separator
    // + "datasource"
    // + File.separator
    // + "salesmanage.json";
    //
    // DaoManager.INSTANCE.initDataSource(dsFileName);
    // ObjectManager bomgr = Kernel.getInstance().getObjectManager();
    // final Dao dao = DbUtils.newDao();
    //
    // // 必须包含id值
    // final int did = NumberUtils.c2int(41, 0);
    // Class<? extends AbstractObject> clazz2 = bomgr.makeAuditObjectClass("AuthEmployeeYaxun");
    // AbstractObject ao = dao.fetch(clazz2, Cnd.where("id", "=", did));
    // ao.afterUpdate(dao, ao);
    // }

}
