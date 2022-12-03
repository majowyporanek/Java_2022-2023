
public interface Segment {
	/**
	 * Kierunek rysowania odcinka. Kierunek ustalany jest poprzez wskazanie
	 * inkrementacji/dekrementacji odpowiedniej wspĂłĹrzÄdnej tablicy reprezentujÄcej
	 * pĹĂłtno, po ktĂłrym rysujemy: <tt>
	 * canvas[ pierwszy indeks ][ drugi_indeks ]
	 * </tt>
	 * 
	 * ObowiÄzuje nastÄpujÄca umowa:
	 * <ul>
	 * <li>1 - inkrementacja pierwszego indeksu tablicy
	 * <li>2 - inkrementacja drugiego indeksu tablicy
	 * <li>-1 - dekrementacja pierwszego indeksu tablicy
	 * <li>-2 - dekrementacja drugiego indeksu tablicy
	 * <li>Inne wartoĹci sÄ niedozwolone i jeĹli wystÄpiÄ odcinek nie powinien byÄ
	 * narysowany na pĹĂłtnie
	 * </ul>
	 * 
	 * @return liczba caĹkowita odpowiadajÄca kierunkowi rysowania
	 */
	public int getDirection();

	/**
	 * Metoda zwraca dĹugoĹÄ odcinka. DĹugoĹÄ to liczba "pikseli", ktĂłre naleĹźÄ do
	 * odcinka.
	 * 
	 * @return liczba "pikseli" wchodzÄcych w skĹad odcinka
	 */
	public int getLength();

	/**
	 * Metoda zwraca liczbÄ caĹkowitÄ reprezentujÄcÄ kolor rysowanego odcinka.
	 * 
	 * @return kolor odcinka
	 */
	public int getColor();
}