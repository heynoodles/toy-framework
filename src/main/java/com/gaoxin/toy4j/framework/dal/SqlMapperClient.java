package com.gaoxin.toy4j.framework.dal;

import com.gaoxin.toy4j.framework.annotation.DaoAction;
import com.gaoxin.toy4j.framework.annotation.DaoParam;
import com.gaoxin.toy4j.framework.annotation.DaoSql;
import com.gaoxin.toy4j.framework.helper.DatabaseHelper;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.lang.ArrayUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author gaoxin.wei
 * sql mapper代理
 */
public class SqlMapperClient {


    public static String regex = "#(.*?)#";

    public static SqlMapperClient INSTANCE = new SqlMapperClient();

    private SqlMapperClient() {}

    public Object execute(Object o, Method method, Object[] objects, MethodProxy methodProxy) {

        try {
            Connection conn = DatabaseHelper.getConnection();
            DaoAction actionType = method.getAnnotation(DaoAction.class);

            RequestParam requestParam = getParams(method, objects);
            if (actionType.action() == DaoActionType.QUERY) {

            } else if (actionType.action() == DaoActionType.INSERT) {
                return doInsert(requestParam, conn);
            }


        } catch (Exception e) {
            System.out.println("hello");
        } finally {

            System.out.println("hahahahah");
        }
        return null;
    }

    private Integer doInsert(RequestParam requestParam, Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(getSql(requestParam.getSql()));

        Map<String, Object> params = requestParam.getParams();
        Set<String> keys = params.keySet();

        Map<String, Integer> paramsIndexMap = getParamsIndexMap(requestParam.getSql());
        for (String key : keys) {
            stmt.setObject(paramsIndexMap.get(key), requestParam.get(key));
        }
        return stmt.executeUpdate();
    }


    private Map<String, Integer> getParamsIndexMap(String sql) {
        Map<String, Integer> paramIndexMap = new HashMap<String, Integer>();
        Pattern pat = Pattern.compile(regex);
        int index = 1;
        for (Matcher m = pat.matcher(sql); m.find(); ) {
            paramIndexMap.put(m.group(1), index++);
        }
        return paramIndexMap;
    }

    private String getSql(String sql) {
        return sql.replaceAll(regex, "?");
    }

    public static void main(String[] args) {
            String result = new String("select * from A where user=#user# and #id#");


            String regx2 = "#(.*?)#";



            Pattern pat = Pattern.compile(regx2);
            for (Matcher m = pat.matcher(result); m.find(); ) {
//                result = result.replace(m.group(1), "");
                System.out.println(m.group(1));
            }


        String s = result.replaceAll(regx2, "?");
        System.out.println(s);

    }

    private RequestParam getParams(Method method, Object[] objects) {
        RequestParam result = new RequestParam();
        if (ArrayUtils.isEmpty(objects)) return result;

        Class<?>[] paramTypes = method.getParameterTypes();
		Annotation[][] paramAnnos = method.getParameterAnnotations();

        for (int i = 0; i < paramTypes.length; i++) {
            Annotation[] annos = paramAnnos[i];

            for (Annotation anno : annos) {
                if (anno instanceof DaoParam) {
                    DaoParam daoParam = (DaoParam) anno;
                    result.add(daoParam.value(), objects[i]);
                }
            }
        }

        DaoSql sql = method.getAnnotation(DaoSql.class);
        result.setSql(sql.value());

        return result;
    }


}
