
public interface Geometry {
	/**
	 * Rozmiar pĹĂłtna (dwuwymiarowej tablicy). Dla uproszczenia zakĹadamy, Ĺźe
	 * tablica jest kwadratowa.
	 * 
	 * @return rozmiar boku pĹĂłtna
	 */
	public int getSize();

	/**
	 * Pierwsza wspĂłĹrzÄdna (indeks) pozycji, od ktĂłrej rysowany bÄdzie pierwszy
	 * odcinek. Pierwszy odcinek zaczyna siÄ na pozycji:
	 * <tt>canvas[ getInitialFirstCoordinate() ][ getInitialSecondCoordinate() ]</tt>
	 * 
	 * @return pierwsza wspĂłĹrzÄdna punktu, od ktĂłrego rozpocznie siÄ rysowanie
	 */
	public int getInitialFirstCoordinate();

	/**
	 * Druga wspĂłĹrzÄdna (indeks) pozycji, od ktĂłrej rysowany bÄdzie pierwszy
	 * odcinek. Pierwszy odcinek zaczyna siÄ na pozycji:
	 * <tt>canvas[ getInitialFirstCoordinate() ][ getInitialSecondCoordinate() ]</tt>
	 * 
	 * @return druga wspĂłĹrzÄdna punktu, od ktĂłrego rozpocznie siÄ rysowanie
	 */
	public int getInitialSecondCoordinate();
}