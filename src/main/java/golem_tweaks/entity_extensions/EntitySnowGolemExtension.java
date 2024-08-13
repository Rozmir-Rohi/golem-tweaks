package golem_tweaks.entity_extensions;

import java.util.ArrayList;

import golem_tweaks.GolemTweaks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

public class EntitySnowGolemExtension extends EntitySnowman implements IShearable
{	

	
	public EntitySnowGolemExtension(final World world)
	{
		super(world);
	}
	
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        
        Double healthToApply = 4.0D;
        if (GolemTweaks.enableSnowGolemBuffs) {healthToApply = (double) GolemTweaks.snowGolemHealth;}
        
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(healthToApply);
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224D);
    }
	
	protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(12, (byte) 1);
    }
	
	public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);
        dataWatcher.updateObject(12, (byte)(nbt.getBoolean("HasPumpkin") ? 1 : 0));
     }
	
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
	      super.writeEntityToNBT(nbt);
	      nbt.setBoolean("HasPumpkin", dataWatcher.getWatchableObjectByte(12) == 1);
	}
	
	protected String getHurtSound()
    {
        return "dig.snow";
    }
	
	protected String getDeathSound()
    {
        return "dig.snow";
    }
		
	public boolean interact(EntityPlayer player)
	{
	    ItemStack itemStack = player.inventory.getCurrentItem();

	    if (itemStack != null)
	    {
	    	if ((GolemTweaks.snowGolemHealAmount > 0) && itemStack.getItem() == Items.snowball) //detect if player is holding healing item
	    	{
	    		if (!player.capabilities.isCreativeMode)
	            {
	                --itemStack.stackSize; //decrease the amount by one
	            }
	    		
	    		healSnowGolem();
	    		
	            if (itemStack.stackSize <= 0)
	            {
	            	player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
	            }

	            return true;
	        }
	    }
	    return super.interact(player);    
	}
	
	public boolean isShearable(ItemStack stack, IBlockAccess world, int x, int y, int z)
	{
	      return hasPumpkin();
	}

    public ArrayList<ItemStack> onSheared(ItemStack itemStack, IBlockAccess world, int x, int y, int z, int fortuneLevel)
    {
		ArrayList<ItemStack> listOfItemStacks = new ArrayList();
		listOfItemStacks.add(new ItemStack(Blocks.pumpkin));
		playSound("mob.sheep.shear", 1.0F, 1.0F);
		setHasPumpkin(false);
		return listOfItemStacks;
    }
	
	
	public boolean attackEntityFrom(DamageSource damageSource, float damage)
	{
        Entity entityThatAttackedThisEntity = damageSource.getSourceOfDamage();
        
        if (	
        		(GolemTweaks.snowGolemHealAmount > 0)
        		&& (entityThatAttackedThisEntity != null)
        		&& (
        				(entityThatAttackedThisEntity instanceof EntitySnowball)
        				|| (entityThatAttackedThisEntity instanceof EntitySnowballExtension)
        			)
        	)
        {
        	healSnowGolem();
        	return true;
        }
        
        return super.attackEntityFrom(damageSource, damage);
    }
    
	public void healSnowGolem()
	{
		if (getHealth() < getMaxHealth())
		{
			heal(GolemTweaks.snowGolemHealAmount);
			extinguish();
			playSound("step.snow", 1.0F, 1.0F); //play healing sound
		}
	}
	
	
	public void attackEntityWithRangedAttack(EntityLivingBase entityLivingBaseToBeAttacked, float p_82196_2_)
    {
		Entity entitySnowball;
		
		entitySnowball = new EntitySnowball(worldObj, this);
		if (GolemTweaks.enableSnowGolemBuffs)
		{
			entitySnowball = new EntitySnowballExtension(worldObj, this);
		}
        
        double xCoordOfTargetEntity = entityLivingBaseToBeAttacked.posX - posX;
        double yCoordOfTargetEntity = entityLivingBaseToBeAttacked.posY + (double) entityLivingBaseToBeAttacked.getEyeHeight() - 1.100000023841858D - entitySnowball.posY;
        double zCoordOfTargetEntity = entityLivingBaseToBeAttacked.posZ - posZ;
        float distanceToTargetEntity = MathHelper.sqrt_double(xCoordOfTargetEntity * xCoordOfTargetEntity + zCoordOfTargetEntity * zCoordOfTargetEntity) * 0.2F;
        
        ((EntitySnowball) entitySnowball).setThrowableHeading(xCoordOfTargetEntity, yCoordOfTargetEntity + (double) distanceToTargetEntity, zCoordOfTargetEntity, 1.6F, 12.0F);
        playSound("random.bow", 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
        worldObj.spawnEntityInWorld(entitySnowball);
    }
	
	public boolean hasPumpkin()
    {
		return (dataWatcher.getWatchableObjectByte(12)) != 0;
    }

    /**
     * set shearing state for pumpkin head
     */
    public void setHasPumpkin(boolean flag)
    {
        byte byteValue = dataWatcher.getWatchableObjectByte(12);

        if (flag)
        {
            dataWatcher.updateObject(12, (byte) 1);
        }
        else
        {
            dataWatcher.updateObject(12, (byte) 0);
        }
    }	
}


	