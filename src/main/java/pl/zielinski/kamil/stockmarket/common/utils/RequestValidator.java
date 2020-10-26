package pl.zielinski.kamil.stockmarket.common.utils;

import pl.zielinski.kamil.stockmarket.common.markers.EntityMarker;

public interface RequestValidator<T extends EntityMarker> {
    boolean validateRequest(T object, int hashcode);

    boolean validateHashcode(int hashcode, int hashcode2);

}
