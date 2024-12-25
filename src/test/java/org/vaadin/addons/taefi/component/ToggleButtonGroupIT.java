package org.vaadin.addons.taefi.component;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.customfield.testbench.CustomFieldElement;
import com.vaadin.flow.component.html.testbench.LabelElement;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.testbench.VerticalLayoutElement;
import com.vaadin.testbench.TestBenchElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.Optional;


public class ToggleButtonGroupIT extends AbstractViewTest {

    @Before
    public void setup() throws Exception {
        super.setup();
        waitForElementPresent(By.ById.id("group120"));
    }

    @Test
    public void group_withLabelAddedToView_groupIsRendered_labelIsRendered() {
        CustomFieldElement group10 = $(CustomFieldElement.class).id("group10");
        Assert.assertNotNull(group10);
        Assert.assertNotNull(group10.getLabel());
        Assert.assertEquals("Menu: [toggleable = false] (ValueChangeListener publishes the selected value)",
                group10.getLabel());
    }

    @Test
    public void registeredValueChangeListener_IsCalledAccordingly() {
        LabelElement selectedValueLabel = $(LabelElement.class).id("group10-selected-value");
        Assert.assertEquals("", selectedValueLabel.getText());

        CustomFieldElement group10 = $(CustomFieldElement.class).id("group10");
        group10.scrollIntoView();
        List<WebElement> groupButtons = getGroupButtons("group10");

        WebElement pastaButton = groupButtons.get(0);
        pastaButton.click();
        Assert.assertNotNull(selectedValueLabel.getText());
        Assert.assertEquals("PASTA", selectedValueLabel.getText());

        WebElement pizzaButton = groupButtons.get(1);
        pizzaButton.click();
        Assert.assertNotNull(selectedValueLabel.getText());
        Assert.assertEquals("PIZZA", selectedValueLabel.getText());

        WebElement burgerButton = groupButtons.get(2);
        burgerButton.click();
        Assert.assertNotNull(selectedValueLabel.getText());
        Assert.assertEquals("BURGER", selectedValueLabel.getText());

        WebElement saladButton = groupButtons.get(3);
        saladButton.click();
        Assert.assertNotNull(selectedValueLabel.getText());
        Assert.assertEquals("SALAD", selectedValueLabel.getText());
    }

    @Test
    public void toggleableGroup_clearsSelection_whenSelectedOptionReClicked() {
        LabelElement selectedValueLabel = $(LabelElement.class).id("group15-selected-value");
        CustomFieldElement group15 = $(CustomFieldElement.class).id("group15");
        group15.scrollIntoView();

        WebElement burgerButton = getGroupButtons("group15").get(2);
        burgerButton.click();
        Assert.assertNotNull(selectedValueLabel.getText());
        Assert.assertEquals("BURGER", selectedValueLabel.getText());

        burgerButton.click(); // Buttons are toggleable, so re-clicking clears the selection
        Assert.assertNotNull(selectedValueLabel.getText());
        Assert.assertEquals("", selectedValueLabel.getText());

        burgerButton.click(); // re-click = re-select:
        Assert.assertNotNull(selectedValueLabel.getText());
        Assert.assertEquals("BURGER", selectedValueLabel.getText());

        burgerButton.click(); // toggle again
        Assert.assertNotNull(selectedValueLabel.getText());
        Assert.assertEquals("", selectedValueLabel.getText());
    }

    @Test
    public void notToggleableGroup_doesNotClearSelection_whenSelectedOptionReClicked() {
        LabelElement selectedValueLabel = $(LabelElement.class).id("group10-selected-value");
        CustomFieldElement group10 = $(CustomFieldElement.class).id("group10");
        group10.scrollIntoView();

        WebElement burgerButton = getGroupButtons("group10").get(2);
        burgerButton.click();
        Assert.assertNotNull(selectedValueLabel.getText());
        Assert.assertEquals("BURGER", selectedValueLabel.getText());

        burgerButton.click(); // Buttons are not toggleable, so re-clicking doesn't clear the selection:
        Assert.assertNotNull(selectedValueLabel.getText());
        Assert.assertEquals("BURGER", selectedValueLabel.getText());

        burgerButton.click(); // Buttons are not toggleable, trying again:
        Assert.assertNotNull(selectedValueLabel.getText());
        Assert.assertEquals("BURGER", selectedValueLabel.getText());
    }

    @Test
    public void notToggleableGroup_valueCanBeCleared_Programmatically() {
        LabelElement selectedValueLabel = $(LabelElement.class).id("group10-selected-value");
        Assert.assertEquals("", selectedValueLabel.getText());
        CustomFieldElement group10 = $(CustomFieldElement.class).id("group10");
        group10.scrollIntoView();

        WebElement pastaButton = getGroupButtons("group10").get(0);
        pastaButton.click();
        Assert.assertNotNull(selectedValueLabel.getText());
        Assert.assertEquals("PASTA", selectedValueLabel.getText());

        pastaButton.click(); // Buttons are not toggleable, so
        Assert.assertNotNull(selectedValueLabel.getText());
        Assert.assertEquals("PASTA", selectedValueLabel.getText());

        ButtonElement clearButton = $(ButtonElement.class).id("clear-group10-selection");
        clearButton.click(); // clearing the selection using setValue
        Assert.assertEquals("", selectedValueLabel.getText());
    }

    @Test
    public void widthFullGroup_hasWidth100PercentStyle() {
        CustomFieldElement group20 = $(CustomFieldElement.class).id("group20");
        Assert.assertTrue(group20.getAttribute("style").contains("width: 100%"));

        VerticalLayoutElement parentLayout = $(VerticalLayoutElement.class).id("parent-layout");
        float parentSize = Float.parseFloat(parentLayout.getCssValue("width").replace("px", ""));
        float groupSize = Float.parseFloat(group20.getCssValue("width").replace("px", ""));
        Assert.assertTrue((groupSize/parentSize) > 0.9f); // due to margins and spacing it doesn't occupy 100%
    }

    @Test
    public void group_withTooltip_tooltipOverlayIsShownWhenFocused() {
        CustomFieldElement group20 = $(CustomFieldElement.class).id("group20");
        group20.scrollIntoView();
        hoverOn(group20);
        Assert.assertTrue("Tooltip for group with id 'group20' should be visible.", isTooltipVisibleFor(group20));
    }

    @Test
    public void group_withItemIconGenerator_hasCorrectIcons() {
        List<WebElement> buttons = getGroupButtons("group30");
        Assert.assertEquals(VaadinIcon.ARROW_CIRCLE_LEFT, getIconForWebElement(buttons.get(0)));
        Assert.assertEquals(VaadinIcon.ARROW_CIRCLE_DOWN, getIconForWebElement(buttons.get(3)));
        Assert.assertEquals(VaadinIcon.ARROW_BACKWARD, getIconForWebElement(buttons.get(5)));
    }

    @Test
    public void group_withDefaultStyle_valueIsSetProgrammatically_hasSelectedItem() {
        List<WebElement> buttons = getGroupButtons("group40");
        Assert.assertEquals("primary", buttons.get(0).getAttribute("theme"));
    }

    @Test
    public void group_withCustomStyleForSelectedItem_valueIsSetProgrammatically_hasSelectedItem() {
        List<WebElement> buttons = getGroupButtons("group80");
        Assert.assertEquals(3, buttons.get(2).getAttribute("class").split("\\s+").length);
    }

    @Test
    public void groupSetToReadonly_canNotChangeValue() {
        LabelElement selectedValueLabel = $(LabelElement.class).id("group50-selected-value");
        Assert.assertEquals("B", selectedValueLabel.getText());

        List<WebElement> buttons = getGroupButtons("group50");

        buttons.get(0).click(); // Button A
        Assert.assertEquals("B", selectedValueLabel.getText());

        buttons.get(1).click(); // Button B
        Assert.assertNotNull(selectedValueLabel.getText()); // readonly group doesn't allow toggling selection as well
        Assert.assertEquals("B", selectedValueLabel.getText());
    }

    @Test
    public void group_withItemTooltipTextGenerator_showsCorrespondentTooltips() {
        List<WebElement> buttons = getGroupButtons("group50");
        hoverOn(buttons.get(0));
        Assert.assertEquals("90 - 100",
                getTooltipFor(buttons.get(0))
                        .map(TestBenchElement::getText)
                        .orElse(""));

        hoverOn(buttons.get(1));
        Assert.assertEquals("75 - 89",
                getTooltipFor(buttons.get(1))
                        .map(TestBenchElement::getText)
                        .orElse(""));

        hoverOn(buttons.get(4));
        Assert.assertEquals("0 - 49",
                getTooltipFor(buttons.get(4))
                        .map(TestBenchElement::getText)
                        .orElse(""));
    }

    @Test
    public void group_withEnabledSetToFalse_hasAllButtonsDisabled() {
        CustomFieldElement group60 = $(CustomFieldElement.class).id("group60");
        group60.scrollIntoView();
        List<WebElement> buttons = getGroupButtons("group60");
        buttons.forEach(button -> Assert.assertFalse(isButtonEnabled(button)));
    }

    @Test
    public void group_withItemEnabledProvider_rendersCorrespondentEnabledButtons() {
        List<WebElement> buttons = getGroupButtons("group70");
        Assert.assertTrue(isButtonEnabled(buttons.get(0)));
        Assert.assertTrue(isButtonEnabled(buttons.get(1)));
        Assert.assertFalse(isButtonEnabled(buttons.get(2)));
        Assert.assertTrue(isButtonEnabled(buttons.get(3)));
        Assert.assertFalse(isButtonEnabled(buttons.get(4)));
    }

    @Test
    public void group_withCustomStyleForSelectedItem_rendersTheSelectedClassAccordingToCustomClassAndNotAsPrimary() {
        List<WebElement> buttons = getGroupButtons("group80");

        buttons.get(0).click();
        Assert.assertEquals(3, buttons.get(0).getAttribute("class").split("\\s+").length);
        Assert.assertNull(buttons.get(0).getAttribute("theme"));

        buttons.get(1).click();
        Assert.assertEquals(3, buttons.get(1).getAttribute("class").split("\\s+").length);
        Assert.assertNull(buttons.get(1).getAttribute("theme"));

        buttons.get(2).click();
        Assert.assertEquals(3, buttons.get(2).getAttribute("class").split("\\s+").length);
        Assert.assertNull(buttons.get(2).getAttribute("theme"));
    }

    @Test
    public void group_withItemOrderProvider_rendersItemsInAccordanceWithOrderProvider() {
        List<WebElement> beforeApplyCustomOrderButtons = getGroupButtons("group90");

        Assert.assertEquals("Not processed", beforeApplyCustomOrderButtons.get(0).getText());
        Assert.assertEquals("Declined", beforeApplyCustomOrderButtons.get(1).getText());
        Assert.assertEquals("Approved", beforeApplyCustomOrderButtons.get(2).getText());

        $(ButtonElement.class).id("custom-order-btn").click();
        List<WebElement> afterApplyCustomOrderButtons = getGroupButtons("group90");

        Assert.assertEquals("Approved", afterApplyCustomOrderButtons.get(0).getText());
        Assert.assertEquals("Not processed", afterApplyCustomOrderButtons.get(1).getText());
        Assert.assertEquals("Declined", afterApplyCustomOrderButtons.get(2).getText());

        $(ButtonElement.class).id("original-order-btn").click();
        List<WebElement> afterApplyOriginalOrderButtons = getGroupButtons("group90");

        Assert.assertEquals("Not processed", afterApplyOriginalOrderButtons.get(0).getText());
        Assert.assertEquals("Declined", afterApplyOriginalOrderButtons.get(1).getText());
        Assert.assertEquals("Approved", afterApplyOriginalOrderButtons.get(2).getText());
    }

    @Test
    public void group_withDefaultOrientation_rendersItemsInHorizontalLayout() {
        CustomFieldElement group90 = $(CustomFieldElement.class).id("group90");
        WebElement layout = group90.findElement(By.tagName("vaadin-horizontal-layout"));
        Assert.assertNotNull(layout);

        Assert.assertThrows(NoSuchElementException.class,
                () -> group90.findElement(By.tagName("vaadin-vertical-layout")));
    }

    @Test
    public void group_withVerticalOrientation_rendersItemsInVerticalLayout() {
        CustomFieldElement group100 = $(CustomFieldElement.class).id("group100");
        WebElement layout = group100.findElement(By.tagName("vaadin-vertical-layout"));
        Assert.assertNotNull(layout);

        Assert.assertThrows(NoSuchElementException.class,
                () -> group100.findElement(By.tagName("vaadin-horizontal-layout")));
    }

    // https://github.com/taefi/toggle-button-group/issues/8
    @Test
    public void customizingGroupAfterSetValue_theStylesRemainInTact() {
        List<WebElement> buttons = getGroupButtons("group120");
        // customization after setting the value should not affect the selected item's style:
        Assert.assertEquals("primary", buttons.get(1).getAttribute("theme"));
        // customizing using setItemTooltipTextGenerator still works:
        hoverOn(buttons.get(0));
        Assert.assertEquals("Answer is yes",
                getTooltipFor(buttons.get(0))
                        .map(TestBenchElement::getText)
                        .orElse(""));

        hoverOn(buttons.get(1));
        Assert.assertEquals("Answer is no",
                getTooltipFor(buttons.get(1))
                        .map(TestBenchElement::getText)
                        .orElse(""));
    }

    private List<WebElement> getGroupButtons(String groupId) {
        CustomFieldElement group = $(CustomFieldElement.class).id(groupId);
        return group.getWrappedElement().findElements(By.tagName("vaadin-button"));
    }

    private boolean isTooltipVisibleFor(WebElement element) {
        return getTooltipFor(element).isPresent();
    }

    private Optional<TestBenchElement> getTooltipFor(WebElement element) {
        String tooltipId = element.getAttribute("aria-describedby");
        if (tooltipId == null) {
            return Optional.empty();
        }
        List<TestBenchElement> tooltips = $("vaadin-tooltip-overlay").all();
        if (tooltips.isEmpty()) {
            return Optional.empty();
        }
        return tooltips.stream()
                .filter(testBenchElement -> testBenchElement.getAttribute("id").equals(tooltipId))
                .findFirst();
    }

    private void hoverOn(WebElement hoverTarget) {
        Actions action = new Actions(getDriver());
        action.moveToElement(hoverTarget).perform();
    }

    private VaadinIcon getIconForWebElement(WebElement element) {
        List<WebElement> icons = element.findElements(By.tagName("vaadin-icon"));
        if (icons.isEmpty()) {
            return null;
        }
        String iconName = icons.get(0).getAttribute("icon").replace("vaadin:", "");
        return VaadinIcon.valueOf(iconName.replace("-", "_").toUpperCase());
    }

    private boolean isButtonEnabled(WebElement button) {
        return !Boolean.parseBoolean(button.getAttribute("disabled"));
    }
}
