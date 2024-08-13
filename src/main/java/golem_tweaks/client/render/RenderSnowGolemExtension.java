package golem_tweaks.client.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import golem_tweaks.entity_extensions.EntitySnowGolemExtension;
import net.minecraft.client.renderer.entity.RenderSnowMan;
import net.minecraft.entity.monster.EntitySnowman;

@SideOnly(Side.CLIENT)
public class RenderSnowGolemExtension extends RenderSnowMan {
	@Override
	protected void renderEquippedItems(EntitySnowman entity, float partialTickTime)
	{
	      if (((EntitySnowGolemExtension) entity).hasPumpkin())
	      {
	         super.renderEquippedItems(entity, partialTickTime); //only render Pumpkin head layer if the Snow Golem has a pumpkin
	      }
	}

}
