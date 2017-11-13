package gusanito;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Red {

	private ArrayList<Enlace> enlaces = new ArrayList<Enlace>();
	private int cantEnlaces;
	
	private ArrayList<Par> pares = new ArrayList<Par>();
	private int cantPares;
	
	private int[][] matrizAdy;
	private int[][] matrizSol;
	private ArrayList<Integer> posiblesInfectadas = new ArrayList<Integer>();
	
	public Red() throws FileNotFoundException {
		Scanner scan = new Scanner(new File("gusano.in"));
		int cantMaxComputadoras = 0;
		int computadora1 = 0;
		int computadora2 = 0;
		int tiempo = 0;
		
		this.cantEnlaces = scan.nextInt();
		
		for (int i = 0 ; i < this.cantEnlaces ; i++) {
			computadora1 = scan.nextInt();
			tiempo = scan.nextInt();
			computadora2 = scan.nextInt();
			
			this.enlaces.add(new Enlace(computadora1, tiempo, computadora2));
			
			if (computadora1 > cantMaxComputadoras) {
				cantMaxComputadoras = computadora1;
			}
			
			if (computadora2 > cantMaxComputadoras) {
				cantMaxComputadoras = computadora2;
			}
		}
		
		this.cantPares = scan.nextInt();
		
		for (int i = 0 ; i < this.cantPares ; i++) {
			computadora1 = scan.nextInt();
			tiempo = scan.nextInt();
			
			this.pares.add(new Par(computadora1, tiempo));
		}
		
		scan.close();
		
		this.matrizAdy = new int[cantMaxComputadoras][cantMaxComputadoras];
		this.matrizSol = new int[cantMaxComputadoras][cantMaxComputadoras];
		
		for (int i = 0 ; i < this.cantEnlaces ; i++) {
			computadora1 = this.enlaces.get(i).getComputadora1()-1;
			computadora2 = this.enlaces.get(i).getComputadora2()-1;
			tiempo = this.enlaces.get(i).getTiempo();
			
			this.matrizAdy[computadora1][computadora2] = tiempo;
			this.matrizAdy[computadora2][computadora1] = tiempo;
		}
	}

	public void resolver() throws IOException {
		int j = 0;
		boolean esCandidato = true;
		floyd();
		for (int i = 0 ; i < this.matrizSol.length ; i++) {
			while (esCandidato && j < this.pares.size()) {
				if (this.matrizSol[i][this.pares.get(j).getComputadora()-1] != this.pares.get(j).getHora()) {
					esCandidato = false;
				}
				j++;
			}
			j = 0;
			if (esCandidato)
				this.posiblesInfectadas.add(i+1);
			esCandidato = true;
		}
		
		escribirSolucion();
	}

	private void floyd() {
		int[][] fAnt = this.matrizAdy.clone();
		int[][] fAct = this.matrizAdy.clone();
		int anterior, actual, ik, kj, minimo;
		
		for (int k = 0 ; k < fAct.length ; k++) {
			for (int i = 0 ; i < fAct.length ; i++) {
				for (int j = 0 ; j < fAct.length ; j++) {
					if (i != k && j != k && i != j) {

						anterior = fAnt[i][j];
						
						ik = fAnt[i][k];
						kj = fAnt[k][j];
						
						if (ik == 0 || kj == 0) {
							actual = 0;
						} else {
							actual = ik + kj;
						}

						if (actual != 0 && (actual < anterior || anterior == 0)) {
							minimo = actual;
						} else {
							minimo = anterior;
						}
						
						fAct[i][j] = minimo;
					}
				}
			}
			fAnt = fAct.clone();
		}
		
		this.matrizSol = fAct.clone();
	}
	
	private void escribirSolucion() throws IOException {
		BufferedWriter buffer = new BufferedWriter(new FileWriter("gusano.out"));
		
		for (int i = 0 ; i < this.posiblesInfectadas.size() ; i++) {
			buffer.write(this.posiblesInfectadas.get(i) + " ");
			buffer.newLine();
		}
		
		buffer.close();
	}
	
	public void mostrarMatAdy() {
		for (int i = 0 ; i < this.matrizAdy.length ; i++) {
			for (int j = 0 ; j < this.matrizAdy.length ; j++) {
				System.out.print(this.matrizAdy[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public void mostrarMatSol() {
		for (int i = 0 ; i < this.matrizSol.length ; i++) {
			for (int j = 0 ; j < this.matrizSol.length ; j++) {
				System.out.print(this.matrizSol[i][j] + " ");
			}
			System.out.println();
		}
	}
}
