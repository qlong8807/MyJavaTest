package utils;

import java.time.Duration;
import java.time.Instant;

public class InstantIUtils {

	public static void main(String[] args) {
		Instant instant = Instant.now();
		System.out.println(instant);
		System.out.println(instant.getEpochSecond());
		System.out.println(instant.getNano());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Instant instant2 = Instant.now();
		Duration between = Duration.between(instant, instant2);
		between = between.plusDays(3L);
		System.out.println(between.getSeconds());
		System.out.println(between.toNanos());
		System.out.println(between.getNano());
	}
}
