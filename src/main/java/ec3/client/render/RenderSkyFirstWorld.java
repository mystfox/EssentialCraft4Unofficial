package ec3.client.render;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import ec3.utils.common.ECUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IRenderHandler;

public class RenderSkyFirstWorld extends IRenderHandler{
	
    private static final ResourceLocation locationMoonPhasesPng = new ResourceLocation("textures/environment/moon_phases.png");
    private static final ResourceLocation locationSunPng = new ResourceLocation("textures/environment/sun.png");
    private static final ResourceLocation locationEndSkyPng = new ResourceLocation("textures/environment/end_sky.png");
    
    public RenderSkyFirstWorld()
    {
        
    }

	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
		int colorDay = 0x213141;
		if(ECUtils.isEventActive("ec3.event.darkness"))
			colorDay = 0x000000;
		 GL11.glDisable(GL11.GL_FOG);
         GL11.glDisable(GL11.GL_ALPHA_TEST);
         GL11.glEnable(GL11.GL_BLEND);
         OpenGlHelper.glBlendFunc(770, 771, 1, 0);
         RenderHelper.disableStandardItemLighting();
         GL11.glDepthMask(false);
         Minecraft.getMinecraft().renderEngine.bindTexture(locationEndSkyPng);
         Tessellator tessellator = Tessellator.instance;

         for (int i = 0; i < 6; ++i)
         {
             GL11.glPushMatrix();

             if (i == 1)
             {
                 GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
             }

             if (i == 2)
             {
                 GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
             }

             if (i == 3)
             {
                 GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
             }

             if (i == 4)
             {
                 GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
             }

             if (i == 5)
             {
                 GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
             }

             tessellator.startDrawingQuads();
             tessellator.setColorOpaque_I(colorDay);
             tessellator.addVertexWithUV(-100.0D, -100.0D, -100.0D, 0.0D, 0.0D);
             tessellator.addVertexWithUV(-100.0D, -100.0D, 100.0D, 0.0D, 16.0D);
             tessellator.addVertexWithUV(100.0D, -100.0D, 100.0D, 16.0D, 16.0D);
             tessellator.addVertexWithUV(100.0D, -100.0D, -100.0D, 16.0D, 0.0D);
             tessellator.draw();
             GL11.glPopMatrix();
         }
         GL11.glDepthMask(true);
         GL11.glEnable(GL11.GL_TEXTURE_2D);
         GL11.glEnable(GL11.GL_ALPHA_TEST);
         
         GL11.glDisable(GL11.GL_TEXTURE_2D);
         Vec3 vec3 = world.getSkyColor(mc.renderViewEntity, partialTicks);
         float f1 = (float)vec3.xCoord;
         float f2 = (float)vec3.yCoord;
         float f3 = (float)vec3.zCoord;
         float f6;

         if (mc.gameSettings.anaglyph)
         {
             float f4 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
             float f5 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
             f6 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
             f1 = f4;
             f2 = f5;
             f3 = f6;
         }

         GL11.glColor3f(f1, f2, f3);
         Tessellator tessellator1 = Tessellator.instance;
         GL11.glDepthMask(false);
         GL11.glEnable(GL11.GL_FOG);
         GL11.glColor3f(f1, f2, f3);
         //GL11.glCallList(this.glSkyList);
         GL11.glDisable(GL11.GL_FOG);
         GL11.glDisable(GL11.GL_ALPHA_TEST);
         GL11.glEnable(GL11.GL_BLEND);
         OpenGlHelper.glBlendFunc(770, 771, 1, 0);
         RenderHelper.disableStandardItemLighting();
         float[] afloat = world.provider.calcSunriseSunsetColors(world.getCelestialAngle(partialTicks), partialTicks);
         float f7;
         float f8;
         float f9;
         float f10;

         if (afloat != null)
         {
             GL11.glDisable(GL11.GL_TEXTURE_2D);
             GL11.glShadeModel(GL11.GL_SMOOTH);
             GL11.glPushMatrix();
             GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
             GL11.glRotatef(MathHelper.sin(world.getCelestialAngleRadians(partialTicks)) < 0.0F ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
             GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
             f6 = afloat[0];
             f7 = afloat[1];
             f8 = afloat[2];
             float f11;

             if (mc.gameSettings.anaglyph)
             {
                 f9 = (f6 * 30.0F + f7 * 59.0F + f8 * 11.0F) / 100.0F;
                 f10 = (f6 * 30.0F + f7 * 70.0F) / 100.0F;
                 f11 = (f6 * 30.0F + f8 * 70.0F) / 100.0F;
                 f6 = f9;
                 f7 = f10;
                 f8 = f11;
             }

             tessellator1.startDrawing(6);
             tessellator1.setColorRGBA_F(f6, f7, f8, afloat[3]);
             tessellator1.addVertex(0.0D, 100.0D, 0.0D);
             byte b0 = 16;
             tessellator1.setColorRGBA_F(afloat[0], afloat[1], afloat[2], 0.0F);

             for (int j = 0; j <= b0; ++j)
             {
                 f11 = (float)j * (float)Math.PI * 2.0F / (float)b0;
                 float f12 = MathHelper.sin(f11);
                 float f13 = MathHelper.cos(f11);
                 tessellator1.addVertex((double)(f12 * 120.0F), (double)(f13 * 120.0F), (double)(-f13 * 40.0F * afloat[3]));
             }

             tessellator1.draw();
             GL11.glPopMatrix();
             GL11.glShadeModel(GL11.GL_FLAT);
         }

         GL11.glEnable(GL11.GL_TEXTURE_2D);
         OpenGlHelper.glBlendFunc(770, 1, 1, 0);
         GL11.glPushMatrix();
         f6 = 1.0F - world.getRainStrength(partialTicks);
         f7 = 0.0F;
         f8 = 0.0F;
         f9 = 0.0F;
         GL11.glColor4f(1.0F, 1.0F, 1.0F, f6);
         GL11.glTranslatef(f7, f8, f9);
         Random sunRnd = new Random(54263524L);
         boolean b = ECUtils.isEventActive("ec3.event.sunArray");
         int mod = 1;
         if(b) mod = 3;
         if(!ECUtils.isEventActive("ec3.event.darkness"))
	         for(int i = 0; i < 10*mod; ++i)
	         {
		         GL11.glPushMatrix();
		         GL11.glRotatef(-90.0F+i*30F, 0.0F+i*i, i-1.0F/i/i, 1.0F*i*i);
		         GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F * i, 1.0F, 0.0F, 0.0F);
		         f10 = sunRnd.nextFloat()*20;
		         mc.renderEngine.bindTexture(locationSunPng);
		         for(int x = 0; x < i+5; ++x)
		         {
			         tessellator1.startDrawingQuads();
			         tessellator1.setColorOpaque_I(sunRnd.nextInt());
			         tessellator1.addVertexWithUV((double)(-f10), 100.0D, (double)(-f10), 0.0D, 0.0D);
			         tessellator1.addVertexWithUV((double)f10, 100.0D, (double)(-f10), 1.0D, 0.0D);
			         tessellator1.addVertexWithUV((double)f10, 100.0D, (double)f10, 1.0D, 1.0D);
			         tessellator1.addVertexWithUV((double)(-f10), 100.0D, (double)f10, 0.0D, 1.0D);
			         tessellator1.draw();
		         }
		         GL11.glPopMatrix();
	         }
	         Random moonRnd = new Random(23564637563453L);
	         if(!ECUtils.isEventActive("ec3.event.darkness"))
		         for(int i = 0; i < 6; ++i)
		         {
			         GL11.glPushMatrix();
			         GL11.glRotatef(-90.0F+i*30F, 0.0F+i*i, i-1.0F/i/i, 1.0F*i*i);
			         GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F * i, 1.0F, 0.0F, 0.0F);
			         f10 = moonRnd.nextFloat()*30F;
			         mc.renderEngine.bindTexture(locationMoonPhasesPng);	        
			         for(int x = 0; x < 2*i; ++x)
			         {
				         int k = moonRnd.nextInt(8);
				         int l = k % 4;
				         int i1 = k / 4 % 2;
				         float f14 = (float)(l + 0) / 4.0F;
				         float f15 = (float)(i1 + 0) / 2.0F;
				         float f16 = (float)(l + 1) / 4.0F;
				         float f17 = (float)(i1 + 1) / 2.0F;
				         tessellator1.startDrawingQuads();
				         tessellator1.setColorRGBA(moonRnd.nextInt(), moonRnd.nextInt(), moonRnd.nextInt(), (int)(moonRnd.nextFloat()*255F));
				         tessellator1.addVertexWithUV((double)(-f10), -100.0D, (double)f10, (double)f16, (double)f17);
				         tessellator1.addVertexWithUV((double)f10, -100.0D, (double)f10, (double)f14, (double)f17);
				         tessellator1.addVertexWithUV((double)f10, -100.0D, (double)(-f10), (double)f14, (double)f15);
				         tessellator1.addVertexWithUV((double)(-f10), -100.0D, (double)(-f10), (double)f16, (double)f15);
				         tessellator1.draw();
			         }
			         GL11.glPopMatrix();
			     }
         GL11.glDisable(GL11.GL_TEXTURE_2D);
         float f18 = world.getStarBrightness(partialTicks) * f6;

         if (f18 > 0.0F)
         {
             GL11.glColor4f(f18, f18, f18, f18);
            // GL11.glCallList(this.starGLCallList);
         }

         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glDisable(GL11.GL_BLEND);
         GL11.glEnable(GL11.GL_ALPHA_TEST);
         GL11.glEnable(GL11.GL_FOG);
         GL11.glPopMatrix();
         GL11.glDisable(GL11.GL_TEXTURE_2D);
         GL11.glColor3f(0.0F, 0.0F, 0.0F);
         double d0 = mc.thePlayer.getPosition(partialTicks).yCoord - world.getHorizon();

         if (d0 < 0.0D)
         {
             GL11.glPushMatrix();
             GL11.glTranslatef(0.0F, 12.0F, 0.0F);
             //GL11.glCallList(this.glSkyList2);
             GL11.glPopMatrix();
             f8 = 1.0F;
             f9 = -((float)(d0 + 65.0D));
             f10 = -f8;
             tessellator1.startDrawingQuads();
             tessellator1.setColorRGBA_I(0, 255);
             tessellator1.addVertex((double)(-f8), (double)f9, (double)f8);
             tessellator1.addVertex((double)f8, (double)f9, (double)f8);
             tessellator1.addVertex((double)f8, (double)f10, (double)f8);
             tessellator1.addVertex((double)(-f8), (double)f10, (double)f8);
             tessellator1.addVertex((double)(-f8), (double)f10, (double)(-f8));
             tessellator1.addVertex((double)f8, (double)f10, (double)(-f8));
             tessellator1.addVertex((double)f8, (double)f9, (double)(-f8));
             tessellator1.addVertex((double)(-f8), (double)f9, (double)(-f8));
             tessellator1.addVertex((double)f8, (double)f10, (double)(-f8));
             tessellator1.addVertex((double)f8, (double)f10, (double)f8);
             tessellator1.addVertex((double)f8, (double)f9, (double)f8);
             tessellator1.addVertex((double)f8, (double)f9, (double)(-f8));
             tessellator1.addVertex((double)(-f8), (double)f9, (double)(-f8));
             tessellator1.addVertex((double)(-f8), (double)f9, (double)f8);
             tessellator1.addVertex((double)(-f8), (double)f10, (double)f8);
             tessellator1.addVertex((double)(-f8), (double)f10, (double)(-f8));
             tessellator1.addVertex((double)(-f8), (double)f10, (double)(-f8));
             tessellator1.addVertex((double)(-f8), (double)f10, (double)f8);
             tessellator1.addVertex((double)f8, (double)f10, (double)f8);
             tessellator1.addVertex((double)f8, (double)f10, (double)(-f8));
             tessellator1.draw();
         }

         if (world.provider.isSkyColored())
         {
             GL11.glColor3f(f1 * 0.2F + 0.04F, f2 * 0.2F + 0.04F, f3 * 0.6F + 0.1F);
         }
         else
         {
             GL11.glColor3f(f1, f2, f3);
         }

         GL11.glPushMatrix();
         GL11.glTranslatef(0.0F, -((float)(d0 - 16.0D)), 0.0F);
         //GL11.glCallList(this.glSkyList2);
         GL11.glPopMatrix();
         GL11.glEnable(GL11.GL_TEXTURE_2D);
         GL11.glDepthMask(true);
     }

}
