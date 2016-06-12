package armas;

import java.util.*;

import projeteis.Projetil;

public abstract class Arma {
	
	protected String nome;
	
	public Arma(String nome){
		this.nome = nome;
	}
	
	public abstract List<Projetil> disparar(double x, double y);
	
	public String getNome(){
		return nome;
	}

	@Override
	public int hashCode() { //baseado no nome
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) { //baseado no nome
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Arma other = (Arma) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	

}
