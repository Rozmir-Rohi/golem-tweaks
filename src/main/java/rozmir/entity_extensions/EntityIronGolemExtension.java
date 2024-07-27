package rozmir.entity_extensions;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import rozmir.GolemTweaks;

public class EntityIronGolemExtension extends EntityIronGolem
{
    private int attackTimer;
    
    public EntityIronGolemExtension(final World world) {
        super(world);
    }
    
    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(17, (byte) 0);
        this.dataWatcher.addObject(18, (byte) 0);
        this.dataWatcher.addObject(19, (byte) 0);
    }
	
	public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);
        dataWatcher.updateObject(17, (byte)(nbt.getBoolean("HasPassedStageOneCracking") ? 1 : 0));
        dataWatcher.updateObject(18, (byte)(nbt.getBoolean("HasPassedStageTwoCracking") ? 1 : 0));
        dataWatcher.updateObject(19, (byte)(nbt.getBoolean("HasPassedStageThreeCracking") ? 1 : 0));
     }
	
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
	      super.writeEntityToNBT(nbt);
	      nbt.setBoolean("HasPassedStageOneCracking", dataWatcher.getWatchableObjectByte(17) == 1);
	      nbt.setBoolean("HasPassedStageTwoCracking", dataWatcher.getWatchableObjectByte(18) == 1);
	      nbt.setBoolean("HasPassedStageThreeCracking", dataWatcher.getWatchableObjectByte(19) == 1);
	}
     
    public boolean interact(EntityPlayer player)
	{
	    ItemStack itemStack = player.inventory.getCurrentItem();

	    if (itemStack != null)
	    {
	    	if ((GolemTweaks.ironGolemHealAmount > 0) && itemStack.getItem() == Items.iron_ingot && (getHealth() < getMaxHealth())) //detect if player is holding healing item
	    	{
	    		if (!player.capabilities.isCreativeMode)
	            {
	                --itemStack.stackSize; //decrease the amount by one
	            }
	    		
	    		heal(GolemTweaks.ironGolemHealAmount);
	    		
	    		playSound("rozmir:iron_golem_heal", 1.0F, 1.0F); //play healing sound
	    		
	            if (itemStack.stackSize <= 0)
	            {
	            	player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
	            }

	            return true;
	        } 
	    }
	    return super.interact(player);    
	}
    
    public void onUpdate()
    {
    	super.onUpdate();
    	
    	if (GolemTweaks.enableIronGolemCracking)
    	{
    		float percentageOfHealth = getHealth(); //since the max health for Iron Golems is 100, there is no need to do the percentage math equation
    		
    		if (hasPassedCrackingStage(1) && (percentageOfHealth > 75))
    		{
    			setPassedCrackingStage(1, false);
    		}

    		if (hasPassedCrackingStage(2) && (percentageOfHealth > 50))
    		{
    			setPassedCrackingStage(2, false);
    		}
    		
    		if (hasPassedCrackingStage(3) && (percentageOfHealth > 25))
    		{
    			setPassedCrackingStage(3, false);
    		}
    	}
    }
    
    public boolean canAttackClass(Class entityClass)
    {
    	if (
    			(
    				EntityPlayer.class.isAssignableFrom(entityClass)
    				&& (isPlayerCreated() || GolemTweaks.enableAllIronGolemsArePassive)
    			)
    			|| EntitySnowGolemExtension.class.isAssignableFrom(entityClass)
    		)
    	{
    		return false;
    	}
        		
    	else
    	{
    		return super.canAttackClass(entityClass);
    	}
    }
    
    public boolean attackEntityFrom(DamageSource damageSource, float damage)
	{   
    	if (GolemTweaks.enableIronGolemCracking)
    	{
    		float percentageOfHealth = getHealth(); //since the max health for Iron Golems is 100, there is no need to do the percentage math equation
    		
    		if (!hasPassedCrackingStage(1) && (percentageOfHealth > 50) && (percentageOfHealth <= 75))
    		{

    			setPassedCrackingStage(1, true);
    			playSound("rozmir:iron_golem_crack", 1.0F, 1.0F);
    		}

    		if (!hasPassedCrackingStage(2) && (percentageOfHealth > 25) && (percentageOfHealth <= 50))
    		{

    			setPassedCrackingStage(2, true);
    			playSound("rozmir:iron_golem_crack", 1.0F, 1.0F);
    		}
    		
    		if (!hasPassedCrackingStage(3) && (percentageOfHealth <= 25))
    		{

    			setPassedCrackingStage(3, true);
    			playSound("rozmir:iron_golem_crack", 1.0F, 1.0F);
    		}
    	}
    	
    	
        return super.attackEntityFrom(damageSource, damage);
    }
    

    public ResourceLocation getTexture() //this is only used if enableIronGolemCracking config is true. This is because the custom renderer is only registered when that config is true.
    {
    	ResourceLocation ironGolemTexture = new ResourceLocation("rozmir:textures/entity/iron_golem.png");
    	
    	if (GolemTweaks.enableIronGolemCracking)
    	{
    		if (hasPassedCrackingStage(1) && !hasPassedCrackingStage(2) && !hasPassedCrackingStage(3))
    		{
    			ironGolemTexture = new ResourceLocation("rozmir:textures/entity/iron_golem_cracking_stage_1.png");
    		}
    		if (hasPassedCrackingStage(1) && hasPassedCrackingStage(2) && !hasPassedCrackingStage(3))
    		{
    			ironGolemTexture = new ResourceLocation("rozmir:textures/entity/iron_golem_cracking_stage_2.png");
    		}
    		if (hasPassedCrackingStage(1) && hasPassedCrackingStage(2) && hasPassedCrackingStage(3))
    		{
    			ironGolemTexture = new ResourceLocation("rozmir:textures/entity/iron_golem_cracking_stage_3.png");
    		}
    	}
    	
    	return ironGolemTexture;
    }
    
    public boolean hasPassedCrackingStage(int stage)
    {
		return (dataWatcher.getWatchableObjectByte(16 + stage)) != 0;
    }
    


    public void setPassedCrackingStage(int stage, boolean flag)
    {
        byte byteValue = dataWatcher.getWatchableObjectByte(17);

        if (flag)
        {
            dataWatcher.updateObject(16+ stage, (byte) 1);
        }
        else
        {
            dataWatcher.updateObject(16 + stage, (byte) 0);
        }
    }	
}