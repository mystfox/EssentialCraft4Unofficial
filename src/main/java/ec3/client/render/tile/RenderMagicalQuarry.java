package ec3.client.render.tile;

import org.lwjgl.opengl.GL11;

import DummyCore.Utils.DrawUtils;
import DummyCore.Utils.TessellatorWrapper;
import ec3.common.tile.TileMagicalQuarry;
import ec3.utils.common.PlayerTickHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMagicalQuarry extends TileEntitySpecialRenderer
{
    public static final ResourceLocation textures = new ResourceLocation("essentialcraft:textures/models/magicianTable.png");

    public RenderMagicalQuarry()
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
        if(p_76986_1_ instanceof TileMagicalQuarry)
        {
        	TileMagicalQuarry tile = (TileMagicalQuarry) p_76986_1_;
        	if(tile.miningX == 0 && tile.miningY == 0 && tile.miningZ == 0)
        	{}else
        	{
        		GlStateManager.pushMatrix();        		
        		float[] o = new float[]{tile.miningX,tile.miningY+0.5F,tile.miningZ};
		        GlStateManager.popMatrix();
		        float f21 = (float)0 + p_76986_9_;
		        float f31 = MathHelper.sin(f21 * 0.2F) / 2.0F + 0.5F;
		        f31 = (f31 * f31 + f31) * 0.2F;
		        float f4;
		        float f5;
		        float f6;
		        GlStateManager.pushMatrix();
				f4 = (float)(o[0] - p_76986_1_.getPos().getX());
			    f5 = (float)(o[1] - (double)(f31 + p_76986_1_.getPos().getY()+1.3F));
				f6 = (float)(o[2] - p_76986_1_.getPos().getZ());
		        GlStateManager.translate((float)p_76986_2_+0.5F, (float)p_76986_4_ + 0.3F, (float)p_76986_6_+0.5F);
		        float f7 = MathHelper.sqrt(f4 * f4 + f6 * f6);
		        float f8 = MathHelper.sqrt(f4 * f4 + f5 * f5 + f6 * f6);
		        GlStateManager.rotate((float)(-Math.atan2((double)f6, (double)f4)) * 180.0F / (float)Math.PI - 90.0F, 0.0F, 1.0F, 0.0F);
		        GlStateManager.rotate((float)(-Math.atan2((double)f7, (double)f5)) * 180.0F / (float)Math.PI - 90.0F, 1.0F, 0.0F, 0.0F);
		        TessellatorWrapper tessellator = TessellatorWrapper.getInstance();
		        RenderHelper.disableStandardItemLighting();
		        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
		        GlStateManager.disableCull();
		        DrawUtils.bindTexture("essentialcraft","textures/special/mru_beam.png");
		        GlStateManager.shadeModel(GL11.GL_SMOOTH);
		        GlStateManager.enableBlend();
		        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		        GlStateManager.disableAlpha();
		        float f9 = 1;
		        float f10 = MathHelper.sqrt(f4 * f4 + f5 * f5 + f6 * f6) / 32.0F - (PlayerTickHandler.tickAmount + p_76986_9_) * 0.1F;
		        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		        tessellator.startDrawing(5);
		        byte b0 = 8;
		
		        for (int i = 0; i <= b0; ++i)
		        {
		            float f11 = MathHelper.sin((float)(i % b0) * (float)Math.PI * 2.0F / (float)b0) * 0.75F * 0.1F;
		            float f12 = MathHelper.cos((float)(i % b0) * (float)Math.PI * 2.0F / (float)b0) * 0.75F * 0.1F;
		            float f13 = (float)(i % b0) * 1.0F / (float)b0;
		            tessellator.addVertexWithUV((double)(f11), (double)(f12), 0.0D, (double)f13, (double)f10);
		            tessellator.addVertexWithUV((double)f11, (double)f12, (double)f8, (double)f13, (double)f9);
		        }
		
		        tessellator.draw();
		        GlStateManager.enableCull();
		        GlStateManager.disableBlend();
		        GlStateManager.shadeModel(GL11.GL_FLAT);
		        GlStateManager.enableAlpha();
		        RenderHelper.enableStandardItemLighting();
		        GlStateManager.popMatrix();
	        }
        }
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
	public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_, int destroyStage) {
		if(p_147500_1_.getBlockMetadata() == 0)
		this.doRender((TileEntity) p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_, 0);
	}

	@Override
	public boolean isGlobalRenderer(TileEntity te) {
		return true;
	}
}