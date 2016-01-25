package com.meizu.test;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// System.out.print(new ReadFromFile().readFileGetMin(Constant.localYesPath));
		int[] number = { 1, 3, 5, 7, 9, 11, 13, 15 };
		boolean flag = false;
		int number1, number2, number3;
		for (int i = 0; i < number.length; i++) {
			number1 = number[i];
			for (int j = 0; j < number.length; j++) {
				number2 = number[j];
				for (int k = 0; k < number.length; k++) {
					number3 = number[k];
					int total = number1 + number2 + number3;
					System.out.println(number1 + "+" + number2 + "+" + number3 + "=" + total);
					if (total == 30) {
						System.out.println("lalala:" + number1 + "," + number2 + "," + number3);
						flag = true;
						break;
					}
				}
				if (flag) {
					break;
				}
			}
			if (flag) {
				break;
			}
		}
	}

}
