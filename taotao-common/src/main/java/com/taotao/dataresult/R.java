package com.taotao.dataresult;

import java.util.HashMap;

/**
 * @author admin
 *	��װ����ֵ������
 *	code�����ؽ��״̬��0���ɹ���1��ʧ��
 *	msg������״̬��Ϣ
 */
public class R extends HashMap{
	
	private int code;
	private String mgs;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMgs() {
		return mgs;
	}
	public void setMgs(String mgs) {
		this.mgs = mgs;
	}
	R(){}
	R(int code,String msg){}
	
	public static R ok() {
		return new R(0,"�ɹ�");
	}
	public static R error() {
		return new R(1,"ʧ��");
	}
	public static R ok(String msg) {
		return new R(0,msg);
	}
	public static R error(String msg) {
		return new R(1,msg);
	}
	@Override
	public R put(Object key, Object value) {
		// TODO Auto-generated method stub
		super.put(key, value);
		return this;
	}
	
	

}
