package com.red.star.macalline.act.admin.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author
 */
public class FileNewUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    private static final String MACALLIEN_FILE_TOKEN_URL = "https://urms.mmall.com/passport/session/";

    private static final String MACALLINE_FILE_EMPLOYEE_ID = "51045443";

    private static final String MACALLINE_FILE_UPLOAD_URL = "https://file-yun.mmall.com/file/public/upload/e";


    /**
     * 获取文件上传token
     *
     * @param empNo
     * @return
     */
    public static String fetchToken(String empNo) {
        String code = "code";
        int successCode = 200;
        String url = MACALLIEN_FILE_TOKEN_URL + empNo;
        Request request = Request.Get(url);
        String resp = HttpUtil.fetch(request);
        JSONObject object = JSONObject.parseObject(resp);
        if (object != null && object.get(code) != null && (Integer) object.get(code) == successCode) {
            return object.get("message").toString();
        }
        return null;
    }

    /**
     * 上传文件
     *
     * @param fileName    文件名
     * @param contentType
     * @param file        文件字节数组
     * @return 文件url
     */
    public static String uploadFile(String fileName, String contentType, byte[] file) {
        String code = "code";
        int successCode = 200;
        String token = fetchToken(MACALLINE_FILE_EMPLOYEE_ID);
        LOGGER.info("上传文件：{},{}", file.length, ObjectUtils.isEmpty(file));
        HttpEntity entity = MultipartEntityBuilder.create()
                .addTextBody("appName", "weixin")
                .addTextBody("token", token)
                .addBinaryBody("file", file, ContentType.parse(contentType), fileName)
                .build();
        Request request = Request.Post(MACALLINE_FILE_UPLOAD_URL).body(entity);
        String resp = HttpUtil.fetch(request);
        JSONObject object = JSONObject.parseObject(resp);
        if (object != null && object.get(code) != null && (Integer) object.get(code) == successCode) {
            String value = object.get("value").toString();
            return JSONObject.parseObject(value).get("fileUrl").toString();
        }
        return null;
    }

    /**
     * 上传文件
     *
     * @param fileName 文件名
     * @param file     文件字节数组
     * @return 文件url
     */
    public static String uploadFile(String fileName, byte[] file) {
        return uploadFile(fileName, ContentType.MULTIPART_FORM_DATA.toString(), file);
    }

    static TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // TODO Auto-generated method stub
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // TODO Auto-generated method stub
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }
    }};

    public class NullHostNameVerifier implements HostnameVerifier {
        /*
         * (non-Javadoc)
         *
         * @see javax.net.ssl.HostnameVerifier#verify(java.lang.String,
         * javax.net.ssl.SSLSession)
         */
        @Override
        public boolean verify(String arg0, SSLSession arg1) {
            // TODO Auto-generated method stub
            return true;
        }
    }

    public static byte[] file2byte(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 判断是否是图片
     * 注意： 该方法适用的图片格式为 bmp/gif/jpg/png
     *
     * @param file
     * @return
     */
    public static Boolean isImage(MultipartFile file) {
        if (ObjectUtils.isEmpty(file)) {
            return false;
        }
        try {
            BufferedImage bi = ImageIO.read(file.getInputStream());
            return bi != null;
        } catch (IOException e) {
            return false;
        }
    }


    public static void main(String args[]) {
        System.out.println(fetchToken("51045443"));
    }
}
