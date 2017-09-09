package ec3.client.render;

import DummyCore.Utils.MiscUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import ec3.common.tile.TileMatrixAbsorber;

@SideOnly(Side.CLIENT)
public class RenderMatrixAbsorber extends TileEntitySpecialRenderer
{
	public static final ResourceLocation textures = new ResourceLocation("essentialcraft:textures/special/models/matrixAbsorber.png");
    public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("essentialcraft:textures/special/models/MatrixAbsorber.obj"));

    public RenderMatrixAbsorber()
    {
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(TileEntity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
    	RenderHelper.disableStandardItemLighting();
    	
        GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_+0.5F, (float)p_76986_4_, (float)p_76986_6_+0.5F);
        float scale = 1F;
        GL11.glScalef(scale, scale, scale);
        this.bindTexture(textures);
        model.renderAll();
        GL11.glPopMatrix();
        
        TileMatrixAbsorber tile = (TileMatrixAbsorber) p_76986_1_;
        float rotation = tile.getWorldObj().getWorldTime() % 360;
        float upperIndex = tile.getWorldObj().getWorldTime() % 360;
        
        if(upperIndex < 180)
        {
        	upperIndex = (180 - upperIndex);
        }else
        {
        	upperIndex -= 180;
        }
        
        rotation = rotation + 360F/tile.getWorldObj().getWorldTime() % 360;
        
    	GL11.glPushMatrix(); 
    	MiscUtils.renderItemStack_Full(tile.getStackInSlot(0),p_76986_1_.xCoord+0.5D,p_76986_1_.yCoord+10D , p_76986_1_.zCoord+0.5D, p_76986_2_, p_76986_4_, p_76986_6_, rotation,0F, 1, 1, 1, 0.5F, 0.25F+((float)upperIndex/500F),0.5F);
    	GL11.glPopMatrix();
    	RenderHelper.enableStandardItemLighting();
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(TileEntity p_110775_1_)
    {
        return textures;
    }

	@Override
	public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_,
			double p_147500_4_, double p_147500_6_, float p_147500_8_) {
		if(p_147500_1_.getWorldObj().getBlockMetadata(p_147500_1_.xCoord, p_147500_1_.yCoord, p_147500_1_.zCoord) == 0)
		this.doRender((TileEntity) p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_, 0);
	}
}