package ec3.common.registry;

import DummyCore.Utils.MiscUtils;
import ec3.common.potion.PotionChaosInfluence;
import ec3.common.potion.PotionFrozenMind;
import ec3.common.potion.PotionMRUCorruption;
import ec3.common.potion.PotionMindfoldParadox;
import ec3.common.potion.PotionPurpleFlame;
import ec3.common.potion.PotionRadiation;
import ec3.common.potion.PotionShadeCorruption;
import ec3.common.potion.PotionUnnormalLightness;
import ec3.common.potion.PotionWindTouch;
import net.minecraft.potion.Potion;

public class PotionRegistry {
	
	public static void registerPotions()
	{
		int pStart = 20;
        pStart = getNextPotionId(pStart);
        if(pStart >= 0)
        {
        	mruCorruptionPotion = new PotionMRUCorruption(pStart, true, 0xff00ff);
        }
        
        pStart = getNextPotionId(pStart);
        if(pStart >= 0)
        {
        	chaosInfluence = new PotionChaosInfluence(pStart, true, 0xff0000);
        }
       
        pStart = getNextPotionId(pStart);
        if(pStart >= 0)
        {
        	frozenMind = new PotionFrozenMind(pStart, true, 0x0000ff);
        }
        
        pStart = getNextPotionId(pStart);
        if(pStart >= 0)
        {
        	windTouch = new PotionWindTouch(pStart, true, 0xccffcc);
        }
        
        pStart = getNextPotionId(pStart);
        if(pStart >= 0)
        {
        	paranormalLightness = new PotionUnnormalLightness(pStart, true, 0xffffcc);
        }
        
        pStart = getNextPotionId(pStart);
        if(pStart >= 0)
        {
        	radiation = new PotionRadiation(pStart, true, 0x660066);
        }
        
        pStart = getNextPotionId(pStart);
        if(pStart >= 0)
        {
        	paradox = new PotionMindfoldParadox(pStart, true, 0xffffff);
        }
        
	}
	
			
    static int getNextPotionId(int start)
    {
        if(Potion.potionTypes != null && start > 0 && start < Potion.potionTypes.length && Potion.potionTypes[start] == null)
            return start;
        if(++start < Potion.potionTypes.length)
            start = getNextPotionId(start);
        else
            start = -1;
        if(start == -1)
        	start = MiscUtils.extendPotionArray(1);
        return start;
    }
    
    public static PotionMRUCorruption mruCorruptionPotion;
    public static PotionFrozenMind frozenMind;
    public static PotionChaosInfluence chaosInfluence;
    public static PotionWindTouch windTouch;
    public static PotionUnnormalLightness paranormalLightness;
    public static PotionRadiation radiation;
    public static PotionShadeCorruption shadeCorruption;
    public static PotionPurpleFlame purpleFlame;
    public static PotionMindfoldParadox paradox;

}
