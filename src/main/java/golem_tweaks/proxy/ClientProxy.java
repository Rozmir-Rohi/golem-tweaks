package golem_tweaks.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import golem_tweaks.GolemTweaks;
import golem_tweaks.client.render.RenderIronGolemExtension;
import golem_tweaks.client.render.RenderSnowGolemExtension;
import golem_tweaks.entity_extensions.EntityIronGolemExtension;
import golem_tweaks.entity_extensions.EntitySnowGolemExtension;

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
