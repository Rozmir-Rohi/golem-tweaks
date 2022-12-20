package rozmir.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.Entity;
import rozmir.entity_extensions.IronGolemExtension;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraftforge.event.entity.living.LivingEvent;

public class ReplaceVanillaIronGolem
{
    @SubscribeEvent
    public void livingUpdate(final LivingEvent.LivingUpdateEvent event)
	{
        if (!event.entityLiving.worldObj.isRemote && event.entityLiving.getClass() == EntityIronGolem.class)
		{
            final IronGolemExtension golem = new IronGolemExtension(event.entityLiving.worldObj);
            golem.copyLocationAndAnglesFrom((Entity)event.entityLiving);
            golem.onSpawnWithEgg((IEntityLivingData)null);
            golem.worldObj.spawnEntityInWorld((Entity)golem);
            event.entityLiving.setDead();
        }
    }
}