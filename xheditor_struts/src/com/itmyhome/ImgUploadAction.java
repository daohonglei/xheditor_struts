package com.itmyhome;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ImgUploadAction extends ActionSupport {
	private String err = "";
	private String msg;              //返回信息
	private File filedata;           //上传文件
	private String filedataFileName; //文件名

	public String imgUpload() {
		//获取response、request对象
		ActionContext ac = ActionContext.getContext();
		HttpServletResponse response = (HttpServletResponse) ac.get(ServletActionContext.HTTP_RESPONSE);
		HttpServletRequest request = (HttpServletRequest) ac.get(ServletActionContext.HTTP_REQUEST);
		
		response.setContentType("text/html;charset=gbk");
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		String saveRealFilePath = ServletActionContext.getServletContext().getRealPath("/upload");
		File fileDir = new File(saveRealFilePath);
		if (!fileDir.exists()) { //如果不存在 则创建 
			fileDir.mkdirs();
		}
		File savefile;
		savefile = new File(saveRealFilePath + "/" + filedataFileName);
		try {
			FileUtils.copyFile(filedata, savefile);
		} catch (IOException e) {
			err = "错误"+e.getMessage();
			e.printStackTrace();
		}
		String fileName = request.getContextPath() + "/upload/" + filedataFileName;
		msg = "{\"err\":\"" + err + "\",\"msg\":\"" + fileName + "\"}";
		out.print(msg); //返回msg信息
		return null;
	}

	public String getErr() {
		return err;
	}

	public void setErr(String err) {
		this.err = err;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public File getFiledata() {
		return filedata;
	}

	public void setFiledata(File filedata) {
		this.filedata = filedata;
	}

	public String getFiledataFileName() {
		return filedataFileName;
	}

	public void setFiledataFileName(String filedataFileName) {
		this.filedataFileName = filedataFileName;
	}


}
