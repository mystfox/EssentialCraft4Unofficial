package ec3.client.gui.element;

import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import ec3.common.tile.TileMagicianTable;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiProgressBar_MagicianTable extends GuiTextField{
	public TileMagicianTable tile;
	
	public GuiProgressBar_MagicianTable(int i, int j, TileEntity table)
	{
		super(i,j);
		tile = (TileMagicianTable) table;
	}

	@Override
	public ResourceLocation getElementTexture() {
		// TODO Auto-generated method stub
		return super.getElementTexture();
	}

	@Override
	public void draw(int posX, int posY) {
		MiscUtils.bindTexture("essentialcraft", "textures/gui/gui_magicianTable.png");
		int current = (int) tile.progressLevel;
		int max = (int) tile.progressRequired;
		int progress = MathUtils.pixelatedTextureSize(current, max, 18);
		this.drawTexturedModalRect(posX, posY, 0, 0, 54, 54);
		this.drawTexturedModalRect(posX, posY+18, 54, 18, progress, 18);
		this.drawTexturedModalRect(posX+18, posY, 72, 0, 18, progress);
		this.drawTexturedModalRect(posX+54-progress, posY+18, 108-progress, 18, progress, 18);
		this.drawTexturedModalRect(posX+18, posY+54-progress, 72, 54-progress, 18, progress);
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return super.getX();
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return super.getY();
	}

	@Override
	public void drawText(int posX, int posY) {
		
	}

}
