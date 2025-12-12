package id.sis.service.accounting.interfacecallback;

import java.util.List;

public interface Callback {
    void onCallbackOrderId(List<String> orderIdList, boolean isError);
}
