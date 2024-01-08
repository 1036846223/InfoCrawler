package zero;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Slf4j
@SpringBootApplication
public class Application {
    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
        System.out.println("application run success");
        log.warn("////////////////////////////////////////////////////////////////////");
        log.warn("//							_ooOoo_								  //");
        log.warn("//						   o8888888o							  //");
        log.warn("//						   88\" . \"88							  //");
        log.warn("//						   (| -_- |)							  //");
        log.warn("//						   O\\  =  /O							  //");
        log.warn("//						____/`---'\\____							  //");
        log.warn("//					  .'  \\|     |//  `.						  //");
        log.warn("//					 /  \\\\|||  :  |||//  \\						  //");
        log.warn("//				    /  _||||| -:- |||||-  \\						  //");
        log.warn("//				    |   | \\\\\\  -  /// |   |						  // ");
        log.warn("//					| \\_|  ''\\---/''  |   |						  //");
        log.warn("//					\\  .-\\__  `-`  ___/-. /						  //");
        log.warn("//				  ___`. .'  /--.--\\ `. . ___					  //");
        log.warn("//				.\"\" '<  `.___\\_<|>_/___.'  >'\"\".				  //");
        log.warn("//			  | | :  `- \\`.;`\\ _ /`;.`/ - ` : | |				  //");
        log.warn("//			  \\  \\ `-.   \\_ __\\ /__ _/   .-` /  /                 //");
        log.warn("//		========`-.____`-.___\\_____/___.-`____.-'========		  //");
        log.warn("//				             `=---='                              //");
        log.warn("//		^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //");
    }
}