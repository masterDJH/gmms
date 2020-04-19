package cn.djh.gmms.web.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 验证码生成器
 * @author Administrator
 *
 */
@Controller
public class CodeController{

	/**
	 * 生成验证码
	 * @param req
	 * @param resp
	 */
	@RequestMapping("/randomCode")
	public void randomCode(HttpServletRequest req, HttpServletResponse resp) {
		//生成 5 位随机数
		String randomCode = UUID.randomUUID().toString().substring(0, 5);

		//把随机数放进Session中,便于登录时比较验证码
		req.getSession().setAttribute("RANDOMCODE_IN_SESSION", randomCode);

		//创建图片对象
		int width = 100;
		int height = 30;
		int imageType = BufferedImage.TYPE_INT_RGB;
		BufferedImage image = new BufferedImage(width, height, imageType);

		//画笔
		Graphics g = image.getGraphics();
		g.setColor(Color.WHITE);
		//绘制一个实心的矩形
		g.fillRect(1, 1, width - 2, height - 2);

		//把随机数画进图片中
		g.setColor(Color.BLUE);//设置随机数的颜色
		Font font = new Font("宋体", Font.BOLD + Font.ITALIC, 25);
		g.setFont(font);//设置随机数的字体和大小
		g.drawString(randomCode, 10, 23);
		//干扰线
		g.setColor(Color.GRAY);
		Random r = new Random();
		for (int i = 0; i < 100; i++) {
			g.fillRect(r.nextInt(width), r.nextInt(height), 2, 2);
		}

		//关闭
		g.dispose();
		//把图片对象以流的方式保存出去
		try {
			ImageIO.write(image, "jpg", resp.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
