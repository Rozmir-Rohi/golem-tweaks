package rozmir.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import rozmir.entity_extensions.SnowGolemExtension;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraftforge.event.entity.living.LivingEvent;


public class ReplaceVanillaSnowGolem
{
	@SubscribeEvent
	public void livingUpdate(final LivingEvent.LivingUpdateEvent event)
	{
        if (!event.entityLiving.worldObj.isRemote && event.entityLiving.getClass() == EntitySnowman.class)
		{
            final SnowGolemExtension golem = new SnowGolemExtension(event.entityLiving.worldObj);
            golem.copyLocationAndAnglesFrom((Entity)event.entityLiving);
            golem.onSpawnWithEgg((IEntityLivingData)null);
            golem.worldObj.spawnEntityInWorld((Entity)golem);
            event.entityLiving.setDead();
        }
    }
}