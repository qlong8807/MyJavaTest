package test;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.bloomfilter.BloomFilterUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.emoji.EmojiUtil;
import cn.hutool.json.JSONUtil;

/**
 * @author zyl
 * @date 2018年11月26日
 * @desc 测试Hutool工具集
 */
public class TestHutool {

	public static void main(String[] args) {
		BitMapBloomFilter createBitMap = BloomFilterUtil.createBitMap(9);
		String formatJsonStr = JSONUtil.formatJsonStr("[{id:1,name:abc},{id:2,name:ccc}]");
//		System.out.println(formatJsonStr);
		String hex = Convert.toHex("abc", CharsetUtil.CHARSET_UTF_8);
		System.out.println(hex);
//		CircleCaptcha createCircleCaptcha = CaptchaUtil.createCircleCaptcha(500, 200);
//		createCircleCaptcha.write("/Users/apple/Downloads/123qwed.png");
		String alias = EmojiUtil.toAlias("😄");
	}
}
