package org.killbill.billing.test.helpers;

import static java.util.UUID.randomUUID;
import static org.killbill.billing.catalog.api.Currency.EUR;

import java.math.BigDecimal;
import java.util.UUID;

import org.killbill.billing.invoice.api.Invoice;
import org.killbill.billing.invoice.api.InvoiceItem;
import org.killbill.billing.invoice.api.InvoiceItemType;
import org.killbill.billing.plugin.api.invoice.PluginInvoiceItem;

public class InvoiceItemBuilder implements Builder<InvoiceItem> {
    Invoice invoice;
    InvoiceItemType type;
    BigDecimal amount;
    Promise<InvoiceItem> linkedItem;

    Promise<InvoiceItem> builtItemHolder;

    @Override
    public InvoiceItem build() {
        String description = "TestNG " + type.name();
        UUID linkedItemId = linkedItem == null ? null : linkedItem.get().getId();
        PluginInvoiceItem item = new PluginInvoiceItem(randomUUID(), type, invoice.getId(), invoice.getAccountId(),
                null, null, amount, EUR, description, null, null, null, null, null, linkedItemId, null, null, null);
        if (builtItemHolder != null) {
            builtItemHolder.resolve(item);
        }
        return item;
    }

    public InvoiceItemBuilder withInvoice(final Invoice invoice) {
        this.invoice = invoice;
        return this;
    }

    public InvoiceItemBuilder withType(final InvoiceItemType type) {
        this.type = type;
        return this;
    }

    public InvoiceItemBuilder withAmount(final BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public InvoiceItemBuilder withLinkedItem(final Promise<InvoiceItem> linkedItem) {
        this.linkedItem = linkedItem;
        return this;
    }

    public InvoiceItemBuilder thenSaveTo(final Promise<InvoiceItem> itemHolder) {
        builtItemHolder = itemHolder;
        return this;
    }
}