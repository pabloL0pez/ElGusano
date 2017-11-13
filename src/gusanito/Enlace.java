package gusanito;

public class Enlace {

	private int computadora1;
	private int tiempo;
	private int computadora2;
	
	public Enlace(int computadora1, int tiempo, int computadora2) {
		this.computadora1 = computadora1;
		this.tiempo = tiempo;
		this.computadora2 = computadora2;
	}

	public int getComputadora1() {
		return computadora1;
	}

	public int getTiempo() {
		return tiempo;
	}

	public int getComputadora2() {
		return computadora2;
	}
	
}
