package GUI.Game.Particule;

import GUI.GameValues;

public class BirCisim extends Alien{



    public BirCisim() {


        super(GameValues.BirCisim_Max_Health,GameValues.BirCisim_Value,GameValues.BirCisim_Radius,GameValues.BirCisim_DeltaX,GameValues.BirCisim_Direction);

        super.setFill(GameValues.BirCisim_Colour);
    }




    @Override
    public void todo() {

    }
}
