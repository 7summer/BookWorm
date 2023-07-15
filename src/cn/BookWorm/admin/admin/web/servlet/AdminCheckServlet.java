package cn.BookWorm.admin.admin.web.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@WebServlet(name="AdminCheckServlet", urlPatterns = "/AdminCheckServlet")
 public class AdminCheckServlet extends HttpServlet
 {
 	private static int WIDTH = 60; //验证码图片宽度
 	private static int HEIGHT = 20; //验证码图片高度
	 @Override
	 public void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException,IOException{
			HttpSession session = request.getSession();
			//设置会话时长
		 	session.setMaxInactiveInterval(60*5);
			response.setContentType("image/jpeg");
			ServletOutputStream sos = response.getOutputStream();

			 //产生随机的认证码
			 char [] rands = generateCheckCode();
			 //将当前验证码存入到Session中
			 session.setAttribute("Admin_check_code", new String(rands));

			//设置浏览器不要缓存此图片
			response.setHeader("Pragma","no-cache");
			response.addHeader("Cache-Control", "must-revalidate");
			response.addHeader("Cache-Control","no-cache");
			response.addHeader("Cache-Control","no-store");
			response.setDateHeader("Expires", 0);
			//创建内存图象并获得其图形上下文
			BufferedImage image =
				new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
			Graphics g = image.getGraphics();

			//产生图像
			drawBackground(g);
			drawRands(g,rands);
			//结束图像的绘制过程，完成图像
			g.dispose();
			//将图像输出到客户端
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(image, "JPEG", bos);
			byte [] buf = bos.toByteArray();
			response.setContentLength(buf.length);
			//下面的语句也可写成：bos.writeTo(sos);
			sos.write(buf);
			bos.close();
			sos.close();
		}
	 @Override
	 protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 this.doGet(req, resp);
	 }

		//生成一个4字符的验证码
 	private char [] generateCheckCode()
 	{
 		//定义验证码的字符表
 		String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		 int length = chars.length();
 		char [] rands = new char[4];
 		for(int i=0; i<4; i++)
 		{
 			int rand = (int)(Math.random() * length);
 			rands[i] = chars.charAt(rand);
 		}
 		return rands;
 	}
 	private void drawRands(Graphics g , char [] rands)
 	{
 		g.setColor(Color.BLACK);
 		g.setFont(new Font(null,Font.ITALIC|Font.BOLD,18));
 		//在不同的高度上输出验证码的每个字符		
 		g.drawString("" + rands[0],1,17);
 		g.drawString("" + rands[1],16,15);
 		g.drawString("" + rands[2],31,18);
 		g.drawString("" + rands[3],46,16);
 		System.out.println(rands);
 	}
 	private void drawBackground(Graphics g)
 	{
  		//画背景
 		g.setColor(new Color(0xDCDCDC));
 		g.fillRect(0, 0, WIDTH, HEIGHT);
 		//随机产生120个干扰点
 		for(int i=0; i<120; i++)
 		{
 			int x = (int)(Math.random() * WIDTH);
 			int y = (int)(Math.random() * HEIGHT);
 			int red = (int)(Math.random() * 255);
 			int green = (int)(Math.random() * 255);
 			int blue = (int)(Math.random() * 255);
 			g.setColor(new Color(red,green,blue));		
 			g.drawOval(x,y,1,0);
 		}
 	}


 }
