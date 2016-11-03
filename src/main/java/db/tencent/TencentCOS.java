/**
 * 
 */
package db.tencent;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.request.CreateFolderRequest;
import com.qcloud.cos.request.ListFolderRequest;
import com.qcloud.cos.request.StatFileRequest;

/**
 * @author zyl
 * @date 2016年9月30日
 * 腾讯云-对象存储服务
 */
public class TencentCOS {
	public static void main(String[] args) {
		COSClient cosClient = new COSClient(10067905,
				"AKIDgKoOo4v3mOq7D7pNk2BE5mIepocMqHTU",
				"zoTyJTvFSVN4fKrDOSPFyWt9GE4lW9mI");
		String bucketName = "bucket1";

		// 创建目录
		CreateFolderRequest createFolderRequest = new CreateFolderRequest(
				bucketName, "/sample_folder/");
		String createFolderRet = cosClient.createFolder(createFolderRequest);
		System.out.println("创建目录：" + createFolderRet);
		// 上传文件
//		UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName,
//				"/sample_folder/sample_file.txt", "F:/Test.java");
//		String uploadFileRet = cosClient.uploadFile(uploadFileRequest);
//		System.out.println("上传文件结果：" + uploadFileRet);
		// 获取文件属性
		StatFileRequest statFileRequest = new StatFileRequest(bucketName,
				"/sample_folder/sample_file.txt");
		String statFileRet = cosClient.statFile(statFileRequest);
		System.out.println("获取文件属性：" + statFileRet);
		// 获取目录列表
		ListFolderRequest listFolderRequest = new ListFolderRequest(bucketName,
				"/sample_folder/");
		String listFolderRet = cosClient.listFolder(listFolderRequest);
		System.out.println("获取目录列表：" + listFolderRet);
	}
}
