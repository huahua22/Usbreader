package com.xwr.usbreader.dev.idCard;

/**
 * 身份证数据对象
 * 
 * @ClassName IDCard
 * @author zhaodianbo@Synjones
 * @date 2013-11-13 上午11:37:27
 *
 */
public class IDCardByte {
	public byte[]  name;
	public byte[] sex;
	public byte[] nation;//外国人代表是国籍或所在地代码
	public byte[] birthday;
	public byte[] address;
	public byte[] idcardno;//外国人代表是永久居留证号码
	public byte[] grantdept;
	public byte[] userlifebegin;
	public byte[] userlifeend;
	public byte[] wlt;
	////////////////港澳
	public byte[] finger;
	public byte[] passno;
	public byte[] signissuecount;
	public byte[] idcardtype;
	////////////////外国人
	public byte[] chineseName;//中文名字
	public byte[] idcardVersion;//证件版本号
	public byte[] applicationAgencyNo;//当次申请受理机关代码


}
