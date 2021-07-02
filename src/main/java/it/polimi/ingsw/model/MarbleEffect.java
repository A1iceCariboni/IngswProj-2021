package it.polimi.ingsw.model;

import java.io.Serializable;

public interface MarbleEffect extends Serializable {
    long serialVersionUID = -8928211276189780625L;

   void giveResourceTo(PlayerBoard playerBoard);
}

