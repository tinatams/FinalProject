public class AssistOne extends Map{
    private boolean buttonOnePress;
    private boolean buttonTwoPress;
    
    public AssistOne(){
        super("assist1");
        buttonOnePress = false;
        buttonTwoPress = false;

    }

    @Override
    protected void loadInteract(){
        interacts = super.interacts;
        interacts.add(new ButtonItem(9,5){
            @Override
            public void actionToDoPressed(){
                buttonOnePress = true;
            }

            @Override
            public void actionToDoReleased(){
                buttonOnePress = false;

            }
        });

        interacts.add(new ButtonItem(12,5){
            @Override
            public void actionToDoPressed(){
                buttonTwoPress = true;
            }

            @Override
            public void actionToDoReleased(){
                buttonTwoPress = false;

            }
        });
    }

    public boolean buttonOne(){
        return buttonOnePress;
    }

    public boolean buttonTwo(){
        return buttonTwoPress;
    }
}