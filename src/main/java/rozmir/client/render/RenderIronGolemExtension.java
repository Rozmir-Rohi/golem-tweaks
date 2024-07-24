package rozmir.client.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderIronGolem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.util.ResourceLocation;
import rozmir.entity_extensions.EntityIronGolemExtension;

@SideOnly(Side.CLIENT)
public class RenderIronGolemExtension extends RenderIronGolem {
	
	protected ResourceLocation getEntityTexture(EntityIronGolem entityIronGolem)
    {
        return ((EntityIronGolemExtension) entityIronGolem).getTexture();
    }
}
