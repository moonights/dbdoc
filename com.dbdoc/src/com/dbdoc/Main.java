package com.dbdoc;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dbdoc.db.model.provider.TableProvider;
import com.dbdoc.utils.FreemarkerUtils;

/***
 * 生成数据说明文档-主函数入口
 * @author moonights
 *
 * @date 2011-11-23
 */
public class Main {
	public static final Logger log = Logger.getLogger(Main.class); 
	public static final String TEMPLEATE_DEFAUTL="template/db_template_default.xml";
	public static final String OUTER_default="c:\\temp-output\\db_document_default.doc";
	
	public static void main(String args[]) throws IOException{
		log.info("<<<<<<<<<<<<<<<<<<<数据库文档生成开始>>>>>>>>>>>>>>>>>>>>>");
		Map propMap=new HashMap();
		try {
			List tables = TableProvider.getInstance().getAllTables();
			propMap.put("tableList", tables);
			FreemarkerUtils.writeTemplateToFile(Main.TEMPLEATE_DEFAUTL, propMap, Main.OUTER_default);
		} catch (SQLException e) {
			// TODO 自动生成 catch 块
			log.info("文档生成中出现错误："+e.toString());
		}
		log.info("<<<<<<<<<<<<<<<<<<<数据库文档生成结束>>>>>>>>>>>>>>>>>>>>>");
		Runtime.getRuntime().exec("cmd.exe /c start c:\\temp-output");
	}
}
