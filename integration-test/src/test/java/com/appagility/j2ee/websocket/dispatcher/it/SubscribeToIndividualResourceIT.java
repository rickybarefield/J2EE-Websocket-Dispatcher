package com.appagility.j2ee.websocket.dispatcher.it;

import java.io.IOException;
import java.util.Set;

import com.google.common.collect.Iterables;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author rbarefield
 */
public class SubscribeToIndividualResourceIT extends SubscribeUnsubscribeBase
{
    @Test
    public void subscriptionToIndividualResourceOnlySendsThatResource() throws IOException, InterruptedException
    {
        String matchingId = createMatchingAndNonMatchingResource().getLeft();

        String subscriptionSuccess = subscribingClient.subscribeExpectingSuccess(matchingId);

        Set<String> idsFromSubscriptionSuccess = getIdsFromSubscriptionSuccess(subscriptionSuccess);

        Assert.assertEquals("Only one resource should have been returned since a resource-id was specified", 1, idsFromSubscriptionSuccess.size());
        Assert.assertEquals(matchingId, Iterables.getOnlyElement(idsFromSubscriptionSuccess));

    }

    private Pair<String, String> createMatchingAndNonMatchingResource() throws IOException, InterruptedException
    {
        String nonMatchingResponse = manipulatingClient.createExpectingSuccess("NonMatchingResource");
        String matchingResponse = manipulatingClient.createExpectingSuccess("MatchingResource");
        return Pair.of(JsonHelpers.ID_OF_RESOURCE.apply(matchingResponse), JsonHelpers.ID_OF_RESOURCE.apply(nonMatchingResponse));
    }

    @Test
    public void subscriptionToIndividualResourceGetsUpdatesForOnlyThatResource() throws IOException, InterruptedException
    {
        Pair<String, String> ids = createMatchingAndNonMatchingResource();
        String matchingId = ids.getLeft();
        String nonMatchingId = ids.getLeft();
        subscribingClient.subscribeExpectingSuccess(matchingId);

        subscribingClient.expectMessages(0);
        manipulatingClient.updateExpectingSuccess(nonMatchingId, "ChangedNonMatchingResource");
        subscribingClient.assertNoMessagesReceived("Should not have received a message since the update was for a different resource");

        subscribingClient.expectMessages(1);
        manipulatingClient.updateExpectingSuccess(matchingId, "ChangedMatchingResource");
        subscribingClient.assertMessagesReceived("No update received for matching resource").get(0);

    }


}
