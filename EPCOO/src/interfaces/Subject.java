package interfaces;

public interface Subject {
	//interface implementada por classes sujeitas a observacao
	public void addObserver(Observer o);
	public void notifyObservers();
}
