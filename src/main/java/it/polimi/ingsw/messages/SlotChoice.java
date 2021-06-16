package it.polimi.ingsw.messages;

public class SlotChoice extends Message{
    public SlotChoice(int slot) {
        super(MessageType.SLOT_CHOICE, Integer.toString(slot));
    }

    public int getSlot(){
        return Integer.parseInt(getPayload());
    }
}
