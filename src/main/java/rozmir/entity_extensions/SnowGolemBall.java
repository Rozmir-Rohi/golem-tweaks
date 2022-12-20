package rozmir.entity_extensions;

import net.minecraft.entity.monster.EntityBlaze;
import rozmir.entity_extensions.SnowGolemExtension;
import rozmir.entity_extensions.IronGolemExtension;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.entity.EntityLivingBase;

public class SnowGolemBall extends EntitySnowball
{
	private int FreezeChance;

	public SnowGolemBall(World world)
    {
        super(world);
    }
	
	public SnowGolemBall(World world, EntityLivingBase entity)
    {
        super(world, entity);
        this.FreezeChance = this.rand.nextInt(100);
    }
	
	@Override
	protected void onImpact(MovingObjectPosition position)
    {
        if (position.entityHit != null)
        {
            byte b0 = 0;

            if (position.entityHit instanceof EntityBlaze)
            {
                b0 = 3;
            }
            
            if (this.FreezeChance <= 10 && !(position.entityHit instanceof IronGolemExtension) && !(position.entityHit instanceof SnowGolemExtension) && !(position.entityHit instanceof EntityPlayer))
            {
            	((EntityLivingBase) position.entityHit).addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 30, 1000));
            	b0 = 1;
            }
            
            if (this.FreezeChance <= 25 && (position.entityHit instanceof EntityBlaze))
            {
            	((EntityLivingBase) position.entityHit).addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 30, 1000));
            	position.entityHit.extinguish();
            	b0 = 9;
            }
            
            position.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float)b0);
        }

        for (int i = 0; i < 8; ++i)
        {
            this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        }

        if (!this.worldObj.isRemote)
        {
            this.setDead();
        }
    }
}
