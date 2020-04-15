package webadv.s17201302.p01;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Please provide an input!");
            System.exit(0);
        }
        else if (args.length >2 ){
            System.err.println("Please provide an input!");
            System.exit(0);
        }
        else if (args.length == 1 ){
            System.out.println(sha256hex(args[0]));
        }
        else {
            Map<String, String> map = readTxtFile("password.txt");
            boolean sign = false;
            for(Map.Entry<String, String> entry : map.entrySet()){
                if (args[0].equals(entry.getKey())&&sha256hex(args[1]).equals(entry.getValue())){
                    System.out.println("登录成功");
                    sign = true;
                }
                else if(args[0].equals(entry.getKey())&& !sha256hex(args[1]).equals(entry.getValue())){
                    System.out.println("登录失败");
                    sign = true;
                }
            }
            if (!sign)  System.out.println("用户不存在");
        }

    }

    public static Map<String, String> readTxtFile(String filePath){
        Map<String, String> map = new HashMap<>();
        try {
            String encoding="UTF-8";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    String[] split = lineTxt.split(" ");
                    map.put(split[0],split[1]);
                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return map;
    }

    public static String sha256hex(String input) {
        return DigestUtils.sha256Hex(input);
    }
}
