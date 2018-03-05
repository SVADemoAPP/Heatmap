package com.sva.common;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;

/**
 * @ClassName: HttpUtil
 * @Description: http相关的应用接口
 * @author zwx177747
 * @date 2015/6/16
 */
public class HttpUtil
{
    private static DatagramSocket udpSocket;

    private static byte[] data = new byte[256];

    private static DatagramPacket udpPacket = new DatagramPacket(data,
            data.length);

    private static Logger Log = Logger.getLogger(HttpUtil.class);

    private X509TrustManager xtm = new X509TrustManager()
    {
        @Override
        public X509Certificate[] getAcceptedIssuers()
        {
            // TODO Auto-generated method stub
            X509Certificate[] a = null;
            return a;
        }

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException
        {
            // TODO Auto-generated method stub

        }

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException
        {
            // TODO Auto-generated method stub

        }
    };

    private HostnameVerifier hv = new HostnameVerifier()
    {
        @Override
        public boolean verify(String arg0, SSLSession arg1)
        {
            return true;
        }
    };

    /**
     * @Title: httpsPost
     * @Description: 发送https请求
     * @param ip
     *            RAN Enabler地址
     * @param appName
     *            应用名称
     * @param password
     *            应用登陆密码
     * @return
     * @throws
     */
    public String httpsPost(String url, String content, String charset)
            throws NoSuchAlgorithmException, KeyManagementException,
            IOException
    {
        Log.debug("httpsPost url:" + url);
        String result = "";
        URL console = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) console.openConnection();

        // SSL验证
        TrustManager[] tm = {xtm};
        System.setProperty("https.protocols", "TLSv1");
        SSLContext ctx = SSLContext.getInstance("TLSv1");
        ctx.init(null, tm, null);
        con.setSSLSocketFactory(ctx.getSocketFactory());
        con.setHostnameVerifier(hv);

        // 属性设置
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestMethod("POST");
        // con.setRequestProperty("Accept-Charset", "utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Content-Type", "application/json");

        // 写入参数
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.write(content.getBytes("UTF-8"));
        out.flush();
        out.close();
        // 接受响应并返回
        InputStream is = con.getInputStream();
        if (is != null)
        {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1)
            {
                outStream.write(buffer, 0, len);
            }
            is.close();
            result = outStream.toString();
            Log.debug(result);
        }
        String token = con.getHeaderField("X-Subject-Token");
        con.disconnect();
        return token;
    }

    /**
     * 
     * @param url
     * @param content
     * @param token
     * @param method
     *            :POST\GET\DELETE
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws IOException
     */
    public String subscription(String url, String content, String token,
            String method) throws NoSuchAlgorithmException,
            KeyManagementException, IOException
    {
        Log.debug("subscription url:" + url);
        String result = "";
        URL console = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) console.openConnection();

        // SSL验证
        TrustManager[] tm = {xtm};
        System.setProperty("https.protocols", "TLSv1");
        SSLContext ctx = SSLContext.getInstance("TLSv1");
        ctx.init(null, tm, null);
        con.setSSLSocketFactory(ctx.getSocketFactory());
        con.setHostnameVerifier(hv);

        // 属性设置
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestMethod(method);
        // con.setRequestProperty("Accept-Charset", "utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("X-Auth-Token", token);

        // 写入参数
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.write(content.getBytes("UTF-8"));
        out.flush();
        out.close();

        // 接受响应并返回
        InputStream is = con.getInputStream();
        if (is != null)
        {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1)
            {
                outStream.write(buffer, 0, len);
            }
            is.close();
            result = outStream.toString();
        }

        con.disconnect();
        return result;
    }

    public static void main(String[] args)
    {
        String url = "https://192.168.200.164:9001/v3/auth/tokens";
        String content = "{\"auth\":{\"identity\":{\"methods\":[\"password\"],\"password\": {\"user\": {\"domain\": \"Api\",\"name\": \"app2\",\"password\": \"User@123456\"}}}}}";
        String charset = "UTF-8";
        HttpUtil capi = new HttpUtil();

        // Thread desktopServerThread = new Thread(new TCPDesktopServer());
        //
        // desktopServerThread.start();
        try
        {
            String to = capi.httpsPost(url, content, charset);
            Logger.getLogger(HttpUtil.class).info(to);

        }
        catch (KeyManagementException e)
        {
            // TODO Auto-generated catch block
            Logger.getLogger(HttpUtil.class).info(e.getStackTrace());
        }
        catch (NoSuchAlgorithmException e)
        {
            // TODO Auto-generated catch block
            Logger.getLogger(HttpUtil.class).info(e.getStackTrace());
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            Logger.getLogger(HttpUtil.class).info(e.getStackTrace());
        }

    }
}
