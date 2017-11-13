package gusanito;

import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		try {
			Red gusano = new Red();
			gusano.resolver();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
