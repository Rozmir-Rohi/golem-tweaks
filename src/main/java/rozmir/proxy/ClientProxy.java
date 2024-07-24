package rozmir.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import rozmir.GolemTweaks;
import rozmir.client.render.RenderIronGolemExtension;
import rozmir.client.render.RenderSnowGolemExtension;
import rozmir.entity_extensions.EntityIronGolemExtension;
import rozmir.entity_extensions.EntitySnowGolemExtension;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerEntityRenderers()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntitySnowGolemExtension.class, new RenderSnowGolemExtension());
		
		if (GolemTweaks.enableIronGolemCracking)
		{
			RenderingRegistry.registerEntityRenderingHandler(EntityIronGolemExtension.class, new RenderIronGolemExtension());
		}
	}
}
