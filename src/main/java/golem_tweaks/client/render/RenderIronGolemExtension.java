package golem_tweaks.client.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import golem_tweaks.entity_extensions.EntityIronGolemExtension;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelIronGolem;
import net.minecraft.client.renderer.entity.RenderIronGolem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderIronGolemExtension extends RenderIronGolem {
	/*
	 * NOTE: This class is only used if the "enableIronGolemCracking" config is enabled
	 */
	
	private static final ResourceLocation baseIronGolemTexture = new ResourceLocation("minecraft:textures/entity/iron_golem/iron_golem.png");
	
	public RenderIronGolemExtension()
    {
        super();
        setRenderPassModel(new ModelIronGolem());
    }
	
	
	protected ResourceLocation getEntityTexture(EntityIronGolem entityIronGolem)
    {
        return baseIronGolemTexture;
    }
	
	protected int shouldRenderPass(EntityLivingBase entityLivingBase, int renderLoopIndex, float partialTickTime)
    {
		EntityIronGolemExtension ironGolemExtension = (EntityIronGolemExtension) entityLivingBase;
		
	    if (renderLoopIndex == 0 && getCrackedTexture(ironGolemExtension) != null)
	    {
	    	bindTexture(getCrackedTexture(ironGolemExtension));
	        return 1;
	    }
	    else
	    {
	    	return -1;
	    }
    }
	
	public ResourceLocation getCrackedTexture(EntityIronGolemExtension ironGolemExtension) //this is only used if enableIronGolemCracking config is true. This is because the custom renderer is only registered when that config is true.
    {
		ResourceLocation ironGolemCrackedTexture = null;
		
		if (ironGolemExtension.hasEnteredInToCrackingStage(1) && !ironGolemExtension.hasEnteredInToCrackingStage(2) && !ironGolemExtension.hasEnteredInToCrackingStage(3))
		{
			ironGolemCrackedTexture = new ResourceLocation("minecraft:textures/entity/iron_golem/iron_golem_crackiness_low.png");
		}
		else if (ironGolemExtension.hasEnteredInToCrackingStage(1) && ironGolemExtension.hasEnteredInToCrackingStage(2) && !ironGolemExtension.hasEnteredInToCrackingStage(3))
		{
			ironGolemCrackedTexture = new ResourceLocation("minecraft:textures/entity/iron_golem/iron_golem_crackiness_medium.png");
		}
		else if (ironGolemExtension.hasEnteredInToCrackingStage(1) && ironGolemExtension.hasEnteredInToCrackingStage(2) && ironGolemExtension.hasEnteredInToCrackingStage(3))
		{
			ironGolemCrackedTexture = new ResourceLocation("minecraft:textures/entity/iron_golem/iron_golem_crackiness_high.png");
		}
		
		return ironGolemCrackedTexture;
    }
}
