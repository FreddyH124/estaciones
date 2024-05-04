package org.arso.interfaces;

public interface IEntidadParseable <T extends IIdentificable >{

    T toEntidad();
}
