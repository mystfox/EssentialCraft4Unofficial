package ec3.client.gui;

import DummyCore.Client.GuiCommon;
import ec3.api.ITEHasMRU;
import ec3.client.gui.element.GuiBalanceState;
import ec3.client.gui.element.GuiBoundGemState;
import ec3.client.gui.element.GuiMRUState;
import ec3.client.gui.element.GuiMRUStorage;
import ec3.client.gui.element.GuiProgressBar_FurnaceMagic;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

public class GuiFurnaceMagic extends GuiCommon{

	public GuiFurnaceMagic(Container c, TileEntity tile) {
		super(c,tile);
		this.elementList.add(new GuiMRUStorage(7, 4, (ITEHasMRU) tile));
		this.elementList.add(new GuiMRUState(25, 58, (ITEHasMRU) tile, 0));
		this.elementList.add(new GuiBalanceState(25, 22, tile));
		this.elementList.add(new GuiBoundGemState(25, 40, tile, 0));
		this.elementList.add(new GuiProgressBar_FurnaceMagic(126, 4, tile));
	}
	
	

}
