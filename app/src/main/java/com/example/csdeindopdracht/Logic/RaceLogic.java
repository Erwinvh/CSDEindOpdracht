package com.example.csdeindopdracht.Logic;

public class RaceLogic {

    public boolean isPlayerBoosted = false;
    public int playerboostCounter = 0;
    public boolean isOponentBoosted = false;
    public int oponentboostCounter = 0;
    public int playerBoostedAmount = 0;


    public void startRace(){

    }

    public void nextPosistion(){
        if (isPlayerBoosted){
            //TODO: update position with topspeed
            playerboostCounter++;
            if (playerboostCounter>5){
                playerboostCounter = 0;
                isPlayerBoosted = false;
            }
        }else{
            //TODO: Update position relative to speed
        }

        if (isOponentBoosted){
            //TODO: update position with topspeed
            oponentboostCounter++;
            if (oponentboostCounter>5){
                oponentboostCounter = 0;
                isOponentBoosted = false;
            }
        }else{
            //TODO: Update position relative to speed

            //TODO: Random chance of oponent boosting. 1/20?
        }





        CrossedFinishLine();
    }

    public void CrossedFinishLine() {
        //TODO: finish
        //if (FinishLinereached == Runner.isPlayer){
        //  victory!
        //  Race.setIsComplete(true);
        //}else if(FinishLineReached == Runner.isPlayer){
        //  Lose!
        //}else{
        //  nothing
        //}
    }

    public void UseSprintButton(Runner runner){
        if (playerBoostedAmount<runner.getStats().getRunDistance()){
            isPlayerBoosted = true;
            playerBoostedAmount++;
        }
    }

    public void ForfeitRace(){
        //TODO: finish
    }
}
