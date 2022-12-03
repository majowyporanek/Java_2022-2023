
public interface SimpleDrawing {
	/**
	 * Metoda umoĹźliwia przekazanie obiektu Geometry.
	 * 
	 * @param input obiekt Geometry
	 */
	public void setCanvasGeometry(Geometry input);

	/**
	 * Metoda zleca narysowanie pojedynczego odcinka. PoczÄtek pierwszego odcinka
	 * wskazuje obiekt Geometry. Kolejny odcinek zaczyna siÄ tam, gdzie poprzedni
	 * siÄ zakoĹczyĹ. Pierwszy punkt nowego odcinka nadpisuje ostatni punkt
	 * poprzedniego. <br>
	 * UWAGA: obszar pĹĂłtna jest ograniczony. JeĹli dĹugoĹÄ zleconego do narysowania
	 * odcinka jest zbyt duĹźa i spowodowaĹaby wyjĹcie poza obszar pĹĂłtna, naleĹźy
	 * odpowiednio dĹugoĹÄ zmniejszyÄ, tak, aby ostatni "piksel" odcinka wypadĹ na
	 * brzegu pĹĂłtna.
	 * 
	 * @param segment - segment do narysowania
	 */
	public void draw(Segment segment);

	/**
	 * Metoda zwraca dwuwymiatowÄ tablicÄ liczb caĹkowitych reprezentujÄcÄ aktualny
	 * stan obrazka. Przed ustawieniem obiektu Geometry metoda zwraca null.
	 * 
	 * @return tablica zawierajÄca obrazek
	 */
	public int[][] getPainting();

	/**
	 * Metoda czyĹci pĹĂłtno (ustawia wszystkie pozycje-"pixele" na 0).
	 */
	public void clear();
}