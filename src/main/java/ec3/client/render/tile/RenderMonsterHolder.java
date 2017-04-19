package ec3.client.render.tile;

import java.util.List;

import DummyCore.Utils.Coord3D;
import DummyCore.Utils.DrawUtils;
import DummyCore.Utils.DummyDistance;
import DummyCore.Utils.MiscUtils;
import DummyCore.Utils.TessellatorWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import ec3.common.tile.TileMonsterHolder;
import ec3.utils.common.PlayerTickHandler;

@SideOnly(Side.CLIENT)
public class RenderMonsterHolder extends TileEntitySpecialRenderer
{
    public static final ResourceLocation textures = new ResourceLocation("essentialcraft:textures/models/magicianTable.png");

    public RenderMonsterHolder()
    {
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    @SuppressWarnings("unchecked")
	public void doRender(TileEntity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
    	RenderHelper.disableStandardItemLighting();
        if(p_76986_1_ instanceof TileMonsterHolder)
        {
        	TileMonsterHolder tile = (TileMonsterHolder) p_76986_1_;
        	List<EntityLivingBase> lst = tile.getWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(tile.getPos().getX()-32, tile.getPos().getY()-32, tile.getPos().getZ()-32, tile.getPos().getX()+33, tile.getPos().getY()+33, tile.getPos().getZ()+33));
        	if(!lst.isEmpty() && tile.getMRU() > lst.size())
        	{
        		for(int i = 0; i < lst.size(); ++i)
        		{
        			EntityLivingBase e = lst.get(i);
        			if(!(e instanceof EntityPlayer))
        			{
        				Coord3D tilePos = new Coord3D(tile.getPos().getX()+0.5D,tile.getPos().getY()+0.5D,tile.getPos().getZ()+0.5D);
        				Coord3D mobPosition = new Coord3D(e.posX,e.posY,e.posZ);
        				DummyDistance dist = new DummyDistance(tilePos,mobPosition);
        				if(dist.getDistance() < 10)
        				{
	        				GlStateManager.pushMatrix(); 
		        			double[] o = new double[]{e.posX-0.5D,e.posY+e.getEyeHeight()+0.5D,e.posZ-0.5D};
		        			float f21 = (float)0 + p_76986_9_;
		      		        float f31 = MathHelper.sin(f21 * 0.2F) / 2.0F + 0.5F;
		      		        f31 = (f31 * f31 + f31) * 0.2F;
		      		        float f4;
		      		        float f5;
		      		        float f6;
		      				f4 = (float)(o[0] - p_76986_1_.getPos().getX());
		      			    f5 = (float)(o[1] - (double)(f31 + p_76986_1_.getPos().getY()+1.3F));
		      				f6 = (float)(o[2] - p_76986_1_.getPos().getZ());
		      		        GlStateManager.translate((float)p_76986_2_+0.5F, (float)p_76986_4_ + 0.6F, (float)p_76986_6_+0.5F);
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
		      		        tessellator.startDrawingWithColor(5);
		      		        byte b0 = 8;
		      		
		      		        for (int i1 = 0; i1 <= b0; ++i1)
		      		        {
		      		            float f11 = MathHelper.sin((float)(i1 % b0) * (float)Math.PI * 2.0F / (float)b0) * 0.75F * 0.1F;
		      		            float f12 = MathHelper.cos((float)(i1 % b0) * (float)Math.PI * 2.0F / (float)b0) * 0.75F * 0.1F;
		      		            float f13 = (float)(i1 % b0) * 1.0F / (float)b0;
		      		            tessellator.setColorRGBA_F(0.0F, 1.0F, 1.0F, 10F);
		      		            tessellator.addVertexWithUV((double)(f11), (double)(f12), 0.0D, (double)f13, (double)f10);
		      		            tessellator.setColorRGBA_F(1.0F, 0.0F, 1.0F, 10F);
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
        		}
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