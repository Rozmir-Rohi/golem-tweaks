package rozmir.entity_extensions;

import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import rozmir.GolemTweaks;

public class EntityIronGolemExtension extends EntityIronGolem
{
    private int attackTimer;
    
    public EntityIronGolemExtension(final World world) {
        super(world);
    }
     
    public boolean interact(EntityPlayer player)
	{
	    ItemStack itemStack = player.inventory.getCurrentItem();

	    if (itemStack != null)
	    {
	    	if (itemStack.getItem() == Items.iron_ingot && getHealth() < getMaxHealth()) //detect if player is holding healing item
	    	{
	    		if (!player.capabilities.isCreativeMode)
	            {
	                --itemStack.stackSize; //decrease the amount by one
	            }
	    		
	    		heal(GolemTweaks.ironGolemHealAmount);
	    		
	    		playSound("random.anvil_use", 0.75F, 1.0F); //play healing sound
	    		
	            if (itemStack.stackSize <= 0)
	            {
	            	player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
	            }

	            return true;
	        } 
	    }
	    return super.interact(player);    
	}
    
    public boolean canAttackClass(Class entityClass)
    {
    	if (EntityPlayer.class.isAssignableFrom(entityClass) || EntitySnowGolemExtension.class.isAssignableFrom(entityClass))
    	{
    		return false;
    	}
        		
    	else
    	{
    		return super.canAttackClass(entityClass);
    	}
    }
    

    public ResourceLocation getTexture() //this is only used if enableIronGolemCracking config is true. This is because the custom renderer is only registered when that config is true.
    {
    	ResourceLocation ironGolemTexture = new ResourceLocation("rozmir:textures/entity/iron_golem.png");
    	
    	if (GolemTweaks.enableIronGolemCracking)
    	{
    		float percentageOfHealth = getHealth(); //since the max health for Iron Golems is 100, there is no need to do the percentage math equation

    		if ((percentageOfHealth > 50) && (percentageOfHealth < 75))
    		{
    			ironGolemTexture = new ResourceLocation("rozmir:textures/entity/iron_golem_cracking_stage_1.png");
    		}
    		if ((percentageOfHealth > 25) && (percentageOfHealth < 50))
    		{
    			ironGolemTexture = new ResourceLocation("rozmir:textures/entity/iron_golem_cracking_stage_2.png");
    		}
    		if (percentageOfHealth < 25)
    		{
    			ironGolemTexture = new ResourceLocation("rozmir:textures/entity/iron_golem_cracking_stage_3.png");
    		}
    	}
    	
    	return ironGolemTexture;
    }
}